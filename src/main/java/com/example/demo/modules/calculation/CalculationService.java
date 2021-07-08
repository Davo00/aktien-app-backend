package com.example.demo.modules.calculation;

import com.example.demo.modules.calculation.response.CreditOverview;
import com.example.demo.modules.calculation.response.WhoOwesWhom;
import com.example.demo.modules.debt.Debt;
import com.example.demo.modules.debt.request.CreateDebt;
import com.example.demo.utils.NotFoundException;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CalculationService {
    List<CreditOverview> calculateOverview(long groupId) throws NotFoundException;
    List<WhoOwesWhom> calculateDebts(long groupId) throws NotFoundException;
    List<CreateDebt> finalCalculation(long groupId) throws NotFoundException;
}
