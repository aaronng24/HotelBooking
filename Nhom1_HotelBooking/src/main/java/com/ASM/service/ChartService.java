package com.ASM.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ASM.model.ChartData.DataPointModel;

@Service
public interface ChartService {

	List<List<DataPointModel>> getCanvasjsChartData();

}
