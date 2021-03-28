package com.minicould.service.parser;

import java.io.IOException;

import com.minicould.service.response.ResponseInfo;
import com.report.parse.data.ResponseReportData;

public interface CResponseParser {

	public ResponseInfo parser(ResponseReportData requestDataArr) throws IOException;
}
