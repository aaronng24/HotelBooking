package com.ASM.service.imp;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ASM.dao.ChartDAO;
import com.ASM.model.ChartData;
import com.ASM.model.ChartData.DataPointModel;

@Service
public class ChartDAOImp implements ChartDAO {
	@Override
	public List<List<DataPointModel>> getCanvasjsChartData() {
		return ChartData.getCanvasjsDataList();
	}
}
