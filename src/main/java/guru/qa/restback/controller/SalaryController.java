package guru.qa.restback.controller;

import guru.qa.restback.domain.ReturnInfo;
import guru.qa.restback.domain.SalaryInfo;
import guru.qa.restback.domain.ShowUserSalaryInfo;
import guru.qa.restback.domain.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;


import static guru.qa.restback.data.UserData.getSalaryInfoById;
import static guru.qa.restback.data.UserData.getUserInfoById;


@RestController
public class SalaryController {





    @GetMapping("user/getSalary")
    @Operation(summary = "Show salary by user id")
    public ShowUserSalaryInfo getSalaryById(@RequestParam Long userId) {
        ShowUserSalaryInfo result = new ShowUserSalaryInfo();

        // search user by id
        UserInfo userInfoById = getUserInfoById(userId);
        if (userInfoById.getReturnInfo().getCode() != ReturnInfo.codes.Ok) {
            result.setReturnInfo(userInfoById.getReturnInfo());
            return result;
        }

        // found user
        result.setName(userInfoById.getName());
        result.setSerName(userInfoById.getSerName());
        result.setBirthDay(userInfoById.getBirthDay());

        // search user salary by id
        SalaryInfo salaryById = getSalaryInfoById(userId);
        if (salaryById.getReturnInfo().getCode() != ReturnInfo.codes.Ok) {
            result.setReturnInfo(salaryById.getReturnInfo());
            return result;
        }

        // found salary
        result.setSalaryUsd(salaryById.getSalaryUsd());
        result.setReturnInfo(ReturnInfo.builder().build());
        return result;

    }


    @PostMapping("user/setSalary")
    @Operation(summary = "Set salary by user id")
    public SalaryInfo setSalaryById(@RequestParam Long userId,
                                    @RequestParam Double salary) {
        SalaryInfo result = new SalaryInfo();
        result.setUserId(userId);
        // search user by id
        UserInfo userInfoById = getUserInfoById(userId);
        if (userInfoById.getReturnInfo().getCode() != ReturnInfo.codes.Ok) {
            result.setReturnInfo(userInfoById.getReturnInfo());
            return result;
        }

        // found user -> set salary
        // find salary for user
        SalaryInfo salaryById = getSalaryInfoById(userId);
        if (salaryById.getReturnInfo().getCode() != ReturnInfo.codes.Ok) {
            result.setReturnInfo(salaryById.getReturnInfo());
            return result;
        }

        salaryById.setSalaryUsd(salary);
        result.setSalaryUsd(salary);
        result.setReturnInfo(ReturnInfo.builder().build());
        return result;
    }

}
