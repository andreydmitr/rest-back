package guru.qa.restback.controller;

import guru.qa.restback.domain.ReturnInfo;
import guru.qa.restback.domain.SalaryInfo;
import guru.qa.restback.domain.ShowUserSalaryInfo;
import guru.qa.restback.domain.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;


@RestController
public class SalaryController {


    private List<UserInfo> userInfo = List.of(
            UserInfo.builder().userId(1L).name("Andrei").serName("Ivanov").birthDay(new Date()).build(),
            UserInfo.builder().userId(2L).name("Sergei").serName("Sinicin").birthDay(new Date()).build(),
            UserInfo.builder().userId(4L).name("Ura").serName("Petrov").birthDay(new Date()).build()
    );

    private List<SalaryInfo> salaryInfo = List.of(
            SalaryInfo.builder().userId(1L).salaryUsd(1000D).build(),
            SalaryInfo.builder().userId(2L).salaryUsd(2000D).build(),
            SalaryInfo.builder().userId(3L).salaryUsd(3000D).build()
    );

    public UserInfo getUserInfoById(Long userId) {
        // search user by id
        UserInfo result = userInfo
                .stream()
                .filter(e -> e.getUserId().equals(userId))
                .findAny()
                .orElse(null);

        if (result == null) {
            result = new UserInfo();
            result.setReturnInfo(ReturnInfo.builder()
                    .text("User has not found")
                    .code(ReturnInfo.codes.ErrorNotFound)
                    .build());
        } else {
            result.setReturnInfo(ReturnInfo.builder().build());
        }
        return result;
    }

    public SalaryInfo getSalaryInfoById(Long userId) {
        // search user by id
        SalaryInfo result = salaryInfo
                .stream()
                .filter(e -> e.getUserId().equals(userId))
                .findAny()
                .orElse(null);

        if (result == null) {
            result= new SalaryInfo();
            result.setReturnInfo(ReturnInfo.builder()
                    .text("Salary for user has not found")
                    .code(ReturnInfo.codes.ErrorNotFound)
                    .build());
        } else {
            result.setReturnInfo(ReturnInfo.builder().build());
        }
        return result;
    }

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
