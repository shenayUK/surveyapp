package com.turkcell.pollservice.service.impl;

import com.turkcell.pollservice.domain.Choice;
import com.turkcell.pollservice.domain.Poll;
import com.turkcell.pollservice.domain.Vote;
import com.turkcell.pollservice.domain.projection.ChoiceVotes;
import com.turkcell.pollservice.exception.ResourceNotFoundException;
import com.turkcell.pollservice.model.PollUser;
import com.turkcell.pollservice.model.converter.PollConverter;
import com.turkcell.pollservice.model.request.PollRequest;
import com.turkcell.pollservice.model.response.ChoiceResponse;
import com.turkcell.pollservice.model.response.PagedResponse;
import com.turkcell.pollservice.model.response.PollResponse;
import com.turkcell.pollservice.model.response.PollSummaryResponse;
import com.turkcell.pollservice.repository.ChoiceRepository;
import com.turkcell.pollservice.repository.PollRepository;
import com.turkcell.pollservice.repository.VoteRepository;
import com.turkcell.pollservice.service.PollService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Slf4j
@Service
public class PollServiceImpl implements PollService {

    private final PollRepository pollRepository;
    private final VoteRepository voteRepository;
    private final ChoiceRepository choiceRepository;
    private final PollConverter pollConverter;

    public PagedResponse<PollSummaryResponse> getAllActivePolls(PollUser currentUser, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "updateTime");
        Page<Poll> polls = pollRepository.findByIsActiveTrue(pageable);

        List<PollSummaryResponse> pollResponses = createPollResponses(currentUser.getId(),polls);

        return new PagedResponse<>(pollResponses, polls.getNumber(),
                            polls.getSize(), polls.getTotalElements(),
                                polls.getTotalPages(), polls.isLast());
    }

    @Override
    public PagedResponse<PollSummaryResponse> getPolls(PollUser currentUser, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "updateTime");
        Page<Poll> polls = pollRepository.findByIsApprovedTrue(pageable);

        List<PollSummaryResponse> pollResponses = createPollResponses(currentUser.getId(),polls);

        return new PagedResponse<>(pollResponses, polls.getNumber(),
                        polls.getSize(), polls.getTotalElements(),
                            polls.getTotalPages(), polls.isLast());
    }

    @Override
    public PagedResponse<PollSummaryResponse> getProposalPolls(PollUser currentUser, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "updateTime");
        Page<Poll> polls = pollRepository.findByIsApprovedFalse(pageable);

        List<PollSummaryResponse> pollResponses = createPollResponses(currentUser.getId(),polls);

        return new PagedResponse<>(pollResponses, polls.getNumber(),
                polls.getSize(), polls.getTotalElements(),
                polls.getTotalPages(), polls.isLast());
    }

    @Override
    public PagedResponse<PollSummaryResponse> getAllPassivePolls(PollUser currentUser, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "updateTime");
        Page<Poll> polls = pollRepository.findByIsActiveFalse(pageable);

        List<PollSummaryResponse> pollResponses = createPollResponses(currentUser.getId(),polls);

        return new PagedResponse<>(pollResponses, polls.getNumber(),
                         polls.getSize(), polls.getTotalElements(),
                                polls.getTotalPages(), polls.isLast());

    }

    @Override
    public PagedResponse<PollSummaryResponse> getPollsForEndUser(PollUser currentUser, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "updateTime");
        Page<Poll> polls = pollRepository.findByIsApprovedTrueAndIsActiveTrue(pageable);

        List<PollSummaryResponse> pollResponses = createPollResponses(currentUser.getId(),polls);

        return new PagedResponse<>(pollResponses, polls.getNumber(),
                polls.getSize(), polls.getTotalElements(),
                polls.getTotalPages(), polls.isLast());
    }


    public PollResponse createPoll(PollRequest pollRequest) {

        Poll poll = Poll.builder().question(pollRequest.getQuestion())
                            .isApproved(true).isActive(true)
                                    .build();

        pollRequest.getChoices().forEach(choiceRequest -> {
            poll.addChoice(Choice.builder().option(choiceRequest.getOption()).build());
        });

       return PollResponse.builder()
                .pollDto(pollConverter
                        .toDto(pollRepository.save(poll)))
                            .success(true).build();
    }

    @Override
    public PollResponse proposePoll(PollUser currentUser,PollRequest pollRequest) {


        Poll poll = Poll.builder().question(pollRequest.getQuestion())
                        .isApproved(false).username(currentUser.getUsername())
                            .build();

        pollRequest.getChoices().forEach(choiceRequest -> {
            poll.addChoice(Choice.builder().option(choiceRequest.getOption()).build());
        });

        return PollResponse.builder()
                .pollDto(pollConverter
                        .toDto(pollRepository.save(poll)))
                             .success(true).build();
    }

    public PollSummaryResponse getPollById(Long pollId, PollUser currentUser) {

        Poll poll = pollRepository.findByIsActiveTrueAndId(pollId)
                        .orElseThrow(() -> new ResourceNotFoundException("Poll", "id", pollId));

        return createPollResponse(currentUser.getId(),poll);
    }

    public void voted(Long pollId, Long choiceId, PollUser currentUser) {

        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new ResourceNotFoundException("Poll", "id", pollId));

        Choice selectedChoice = poll.getChoices().stream()
                  .filter(choice -> choice.getId().equals(choiceId))
                    .findFirst()
                       .orElseThrow(() -> new ResourceNotFoundException("Choice", "id", choiceId));

        Vote vote = Vote.builder().poll(poll)
                            .userId(currentUser.getId())
                                .choice(selectedChoice).build();

        voteRepository.save(vote);


    }

    @Override
    public void deletePoll(Long id) {

        try{
            voteRepository.deleteByPollId(id);
            choiceRepository.deleteByPollId(id);
            pollRepository.deleteByIdentifier(id);
        }catch(Throwable ex){
            throw new RuntimeException("Silme sırasında hata oluştu");
        }

    }

    @Override
    public void doPassivePoll(Long id) {

        Poll poll = pollRepository.findById(id).orElseThrow(()->new RuntimeException("Pasife alma sırasında hata oluştu"));
        poll.setIsActive(false);
        pollRepository.save(poll);

    }

    @Override
    public void confirmPoll(Long id) {

        Poll poll = pollRepository.findById(id).orElseThrow(()->new RuntimeException("Pasife alma sırasında hata oluştu"));
        poll.setIsActive(true);
        poll.setIsApproved(true);
        pollRepository.save(poll);

    }


    private Map<Long, Long> getChoiceVoteCountMap(List<Long> pollIds) {

        return voteRepository.countByPollIdInGroupByChoiceId(pollIds).stream()
                .collect(Collectors.toMap(ChoiceVotes::getChoiceId, ChoiceVotes::getVoteCount));
    }

    private Map<Long, Long> getPollUserVoteMap(Long userId, List<Long> pollIds) {

        return voteRepository.findByUserIdAndPollIdIn(userId, pollIds).stream()
                    .collect(Collectors.toMap(vote -> vote.getPoll().getId(), vote -> vote.getChoice().getId()));

    }

    public   List<PollSummaryResponse>  createPollResponses(Long userId, Page<Poll> polls ) {

        List<Long> pollIds = polls.map(Poll::getId).getContent();
        Map<Long, Long> choiceVotes = getChoiceVoteCountMap(pollIds);
        Map<Long, Long> pollUserVotes = getPollUserVoteMap(userId, pollIds);

        List<PollSummaryResponse> pollResponses = polls.map(poll ->
                        buildPollResponse(poll,choiceVotes,pollUserVotes.get(poll.getId()))).getContent();

        return pollResponses;
    }

    public   PollSummaryResponse createPollResponse(Long userId, Poll poll ) {

        Long pollId = poll.getId();
        Map<Long, Long> choiceVotes = getChoiceVoteCountMap(Collections.singletonList(pollId));
        Map<Long, Long> pollUserVotes = getPollUserVoteMap(userId, Collections.singletonList(pollId));

        return buildPollResponse(poll,choiceVotes,pollUserVotes.get(pollId));
    }


    public static PollSummaryResponse buildPollResponse(Poll poll, Map<Long, Long> choiceVotes, Long userVote) {


        PollSummaryResponse response = PollSummaryResponse
                .builder().id(poll.getId())
                    .question(poll.getQuestion())
                        .username(poll.getUsername())
                            .selectedChoice(userVote)
                              .isActive(poll.getIsActive())
                                .updateTime(poll.getUpdateTime())
                                    .build();


        List<ChoiceResponse> choiceResponses = poll.getChoices().stream().map(choice -> {
            ChoiceResponse choiceResponse = ChoiceResponse.builder()
                                .optionNo(choice.getId())
                                   .responseCount(choiceVotes.containsKey(choice.getId()) ? choiceVotes.get(choice.getId()) : 0L)
                                    .option(choice.getOption()).build();

            return choiceResponse;
        }).collect(Collectors.toList());

        response.setChoices(choiceResponses);
        response.setTotalVotes(choiceResponses
                    .stream().mapToLong(ChoiceResponse::getResponseCount).sum());

        return response;
    }
}
