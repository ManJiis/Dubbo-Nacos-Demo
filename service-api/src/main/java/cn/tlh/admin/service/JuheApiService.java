package cn.tlh.admin.service;

import cn.tlh.admin.common.base.common.JuheResponse;

public interface JuheApiService {

    JuheResponse getTodayInHistory();

    JuheResponse getPhoneAttribution(String phone);

    JuheResponse getAdministrativeDivisions(String id);

    JuheResponse getSimpleWeather(String city);
}
