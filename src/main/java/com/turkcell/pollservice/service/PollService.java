package com.turkcell.pollservice.service;


import com.turkcell.pollservice.model.PollUser;
import com.turkcell.pollservice.model.request.PollRequest;
import com.turkcell.pollservice.model.response.PagedResponse;
import com.turkcell.pollservice.model.response.PollResponse;
import com.turkcell.pollservice.model.response.PollSummaryResponse;
import org.springframework.stereotype.Component;


@Component
public interface PollService {

    PagedResponse<PollSummaryResponse> getAllActivePolls(PollUser currentUser, int page, int size);
    PagedResponse<PollSummaryResponse> getPolls(PollUser currentUser, int page, int size);
    PagedResponse<PollSummaryResponse> getProposalPolls(PollUser currentUser, int page, int size);
    PagedResponse<PollSummaryResponse> getAllPassivePolls(PollUser currentUser, int page, int size);
    PagedResponse<PollSummaryResponse> getPollsForEndUser(PollUser currentUser, int page, int size);
    PollResponse createPoll(PollRequest pollRequest);
    PollResponse proposePoll(PollUser currentUser,PollRequest pollRequest);
    PollSummaryResponse getPollById(Long pollId, PollUser currentUser);
    void voted(Long pollId, Long choiceId, PollUser currentUser);
    void deletePoll(Long id);
    void doPassivePoll(Long id);
    void confirmPoll(Long id);

}

