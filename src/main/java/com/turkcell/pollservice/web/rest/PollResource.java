package com.turkcell.pollservice.web.rest;

import com.turkcell.pollservice.auth.UserPrincipal;
import com.turkcell.pollservice.model.request.PollRequest;
import com.turkcell.pollservice.model.response.PagedResponse;
import com.turkcell.pollservice.model.response.PollResponse;
import com.turkcell.pollservice.model.response.PollSummaryResponse;
import com.turkcell.pollservice.service.PollService;
import com.turkcell.pollservice.util.ModalMapper;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/polls")
@Api(value="Poll API")
@RequiredArgsConstructor
public class PollResource {

    private final PollService pollService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedResponse<PollSummaryResponse>> getPolls(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                       @RequestParam(value = "size", defaultValue = "10") int size)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();

        return ResponseEntity.ok().body(pollService.getPolls(ModalMapper.getPollUser(principal), page, size));
    }

    @GetMapping("/proposal")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedResponse<PollSummaryResponse>> getProposalPolls(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                               @RequestParam(value = "size", defaultValue = "10") int size)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();

        return ResponseEntity.ok().body(pollService.getProposalPolls(ModalMapper.getPollUser(principal), page, size));
    }

    @GetMapping("/enduser")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<PagedResponse<PollSummaryResponse>> getEndUserPolls(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                              @RequestParam(value = "size", defaultValue = "10") int size)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();

        return ResponseEntity.ok().body(pollService.getPollsForEndUser(ModalMapper.getPollUser(principal), page, size));
    }


    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PollResponse> createPoll(@Valid @RequestBody PollRequest pollRequest) {

        return  ResponseEntity.ok().body(pollService.createPoll(pollRequest));
    }

    @PostMapping("/propose")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<PollResponse> proposePoll(@Valid @RequestBody PollRequest pollRequest) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();

        return  ResponseEntity.ok().body(pollService.proposePoll(ModalMapper.getPollUser(principal),pollRequest));
    }

    @GetMapping("/{pollId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public PollSummaryResponse getPollById(@PathVariable Long pollId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();

        return pollService.getPollById(pollId, ModalMapper.getPollUser(principal));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{pollId}/delete")
    public ResponseEntity deletePoll(@PathVariable Long pollId) {

        pollService.deletePoll(pollId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{pollId}/doPassive")
    public ResponseEntity doPassive(@PathVariable Long pollId) {

        pollService.doPassivePoll(pollId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{pollId}/confirm")
    public ResponseEntity doConfirm(@PathVariable Long pollId) {

        pollService.confirmPoll(pollId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/{pollId}/{choiceId}/voted")
    public ResponseEntity voted(@PathVariable Long pollId,@PathVariable Long choiceId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();

        pollService.voted(pollId,choiceId,ModalMapper.getPollUser(principal));
        return ResponseEntity.ok().build();
    }


}
