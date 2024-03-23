package com.ASM.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ASM.model.ChartData.DataPointModel;

@Service
public interface ChartDAO {
	 
	List<List<DataPointModel>> getCanvasjsChartData();
 
}
