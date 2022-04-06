package com.eyes.eyesblog.service.impl;

import com.eyes.eyesblog.entity.Copywriting;
import com.eyes.eyesblog.mapper.CopywritingMapper;
import com.eyes.eyesblog.service.CopywritingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CopywritingServiceImpl implements CopywritingService {
    @Autowired
    private CopywritingMapper copywritingMapper;

    public List<Copywriting> getCopyWriting() {
        List<Copywriting> list = copywritingMapper.findAll();
        return list;
    }
}
