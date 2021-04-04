package top.b0x0.admin.service;


import top.b0x0.admin.common.vo.JuheResponse;

public interface JuheApiService {

    JuheResponse getTodayInHistory();

    JuheResponse getPhoneAttribution(String phone);

    JuheResponse getAdministrativeDivisions(String id);

    JuheResponse getSimpleWeather(String city);
}
