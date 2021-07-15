package com.example.demo.modules.calculation;

import com.example.demo.modules.calculation.response.CreditOverview;
import com.example.demo.modules.calculation.response.WhoOwesWhom;
import com.example.demo.modules.debt.Debt;
import com.example.demo.modules.debt.request.CreateDebt;
import com.example.demo.modules.debt.response.DebtResponse;
import com.example.demo.utils.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/calculate")
public class CalculationController {

    @Autowired
    CalculationService calculationService;

    @GetMapping("/overview/{groupId}")
    public ResponseEntity<List<CreditOverview>> calculateOverview(@PathVariable("groupId") long groupId) throws NotFoundException {
        return ResponseEntity.ok(calculationService.calculateOverview(groupId));
    }

    @GetMapping("/debts/{groupId}")
    public ResponseEntity<List<WhoOwesWhom>> calculateDebts(@PathVariable("groupId") long groupId) throws NotFoundException {
        return ResponseEntity.ok(calculationService.calculateDebts(groupId));
    }

    @PutMapping("/final/{groupId}")
    public ResponseEntity<List<DebtResponse>> finalCalculation(@PathVariable("groupId") long groupId) throws NotFoundException {
        return ResponseEntity.ok(calculationService.finalCalculation(groupId));
    }

}
