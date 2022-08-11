package guru.qa.restback.data;

import guru.qa.restback.domain.ReturnInfo;
import guru.qa.restback.domain.SalaryInfo;
import guru.qa.restback.domain.UserInfo;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;


public class UserData {


    public static List<UserInfo> userInfo = List.of(
            UserInfo.builder().userId(1L).name("Andrei").serName("Ivanov")
                    .birthDay(StringToDate("11/12/1980")).build(),
            UserInfo.builder().userId(2L).name("Sergei").serName("Sinicin")
                    .birthDay(StringToDate("11/10/1985")).build(),
            UserInfo.builder().userId(4L).name("Ura").serName("Petrov")
                    .birthDay(StringToDate("01/12/1996")).build()
    );

    public static List<SalaryInfo> salaryInfo = List.of(
            SalaryInfo.builder().userId(1L).salaryUsd(1000D).build(),
            SalaryInfo.builder().userId(2L).salaryUsd(2000D).build(),
            SalaryInfo.builder().userId(3L).salaryUsd(3000D).build()
    );

    public static Date StringToDate(String s){

        Date result = null;
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            result  = dateFormat.parse(s);
        }

        catch(ParseException e){
            e.printStackTrace();
        }
        return result ;
    }

    public static UserInfo getUserInfoById(Long userId) {
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

    public static SalaryInfo getSalaryInfoById(Long userId) {
        // search user by id
        SalaryInfo result = salaryInfo
                .stream()
                .filter(e -> e.getUserId().equals(userId))
                .findAny()
                .orElse(null);

        if (result == null) {
            result = new SalaryInfo();
            result.setReturnInfo(ReturnInfo.builder()
                    .text("Salary for user has not found")
                    .code(ReturnInfo.codes.ErrorNotFound)
                    .build());
        } else {
            result.setReturnInfo(ReturnInfo.builder().build());
        }
        return result;
    }

}
