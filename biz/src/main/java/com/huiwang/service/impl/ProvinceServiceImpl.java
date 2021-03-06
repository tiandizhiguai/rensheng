package com.huiwang.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.huiwang.dao.ProvinceDao;
import com.huiwang.model.ProvinceModel;
import com.huiwang.service.ProvinceService;
import com.huiwang.vo.Province;

@Service
public class ProvinceServiceImpl implements ProvinceService {

    @Resource
    private ProvinceDao provinceDao;

    @Override
    public List<Province> getAll() {
        List<Province> vos = new ArrayList<Province>();
        List<ProvinceModel> models = provinceDao.getAll();

        if (!CollectionUtils.isEmpty(models)) {
            for (ProvinceModel model : models) {
                vos.add(model2VO(model));
            }
        }

        return vos;
    }

    public Province model2VO(ProvinceModel model) {
        Province vo = new Province();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }
}
