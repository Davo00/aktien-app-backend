package com.example.demo.modules.debt;

import com.example.demo.modules.debt.request.CreateDebt;
import com.example.demo.modules.debt.request.ProposeDebt;
import com.example.demo.modules.debt.response.DebtResponse;
import com.example.demo.modules.user.User;
import com.example.demo.modules.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/debt")
public class DebtController {


    private DebtService debtService;
    private UserService userService;

    @Autowired
    DebtController(DebtService debtService, UserService userService) {
        this.debtService = debtService;
        this.userService = userService;

    }

    @GetMapping
    public ResponseEntity<List<DebtResponse>> findAllDebt() {
        return ResponseEntity.ok(debtService.findAllDebt());
    }

    @PostMapping
    public ResponseEntity<DebtResponse> createDebt(@RequestBody @Valid CreateDebt request,
                                                   UriComponentsBuilder uriComponentsBuilder) throws Exception {
        DebtResponse debt = debtService.createDebt(request);
        UriComponents uriComponents = uriComponentsBuilder.path("debt/{id}").buildAndExpand(debt.getId());
        URI location = uriComponents.toUri();
        return ResponseEntity.created(location).body(debt);
    }

    @PutMapping("propose")
    public ResponseEntity<DebtResponse> proposeDebt(@RequestHeader("Authorization") String token,
                                      @RequestBody @Valid ProposeDebt proposeDebt,
                                      UriComponentsBuilder uriComponentsBuilder) throws Exception {
        User user = userService.getCurrentUser(token);
        DebtResponse debtResponse = debtService.proposeDebt(user, proposeDebt);
        UriComponents uriComponents = uriComponentsBuilder.path("debt/{id}").buildAndExpand(proposeDebt.getDebtId());
        URI location = uriComponents.toUri();
        return ResponseEntity.created(location).body(debtResponse);
    }

    @PutMapping("accept/{debtId}")
    public ResponseEntity<DebtResponse> acceptDebt(@RequestHeader("Authorization") String token,
                                                   @PathVariable("debtId") long debtId) throws Exception {
        User user = userService.getCurrentUser(token);
        return ResponseEntity.ok(debtService.acceptDebt(debtId, user.getId()));
    }
}
