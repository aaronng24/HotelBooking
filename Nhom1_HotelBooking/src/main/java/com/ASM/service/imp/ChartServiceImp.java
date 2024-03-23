package com.ASM.service.imp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ASM.dao.ChartDAO;
import com.ASM.model.ChartData.DataPointModel;
import com.ASM.service.ChartService;

@Service
public class ChartServiceImp implements ChartService {
	@Autowired
	private ChartDAO canvasjsChartDao;
 
	public void setCanvasjsChartDao(ChartDAO canvasjsChartDao) {
		this.canvasjsChartDao = canvasjsChartDao;
	}
 
	@Override
	public List<List<DataPointModel>> getCanvasjsChartData() {
		return canvasjsChartDao.getCanvasjsChartData();
	}
}
