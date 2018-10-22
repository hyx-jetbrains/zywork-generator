package top.zywork.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zywork.dao.{zywork.beanName}{zywork.daoSuffix};
import top.zywork.dos.{zywork.beanName}{zywork.doSuffix};
import top.zywork.dto.{zywork.beanName}{zywork.dtoSuffix};
import top.zywork.service.AbstractBaseService;
import top.zywork.service.{zywork.beanName}{zywork.serviceSuffix};

import javax.annotation.PostConstruct;

/**
 * {zywork.beanName}{zywork.suffix}服务接口实现类<br/>
 *
 * 创建于{zywork.createDate}<br/>
 *
 * @author {zywork.author}
 * @version 1.0
 */
@Service(value = "{zywork.beanNameLowerCase}{zywork.serviceSuffix}")
public class {zywork.beanName}{zywork.suffix} extends AbstractBaseService implements {zywork.beanName}{zywork.serviceSuffix} {

    private {zywork.beanName}{zywork.daoSuffix} {zywork.beanNameLowerCase}{zywork.daoSuffix};

    @Autowired
    public void set{zywork.beanName}{zywork.daoSuffix}({zywork.beanName}{zywork.daoSuffix} {zywork.beanNameLowerCase}{zywork.daoSuffix}) {
        super.setBaseDAO({zywork.beanNameLowerCase}{zywork.daoSuffix});
        this.{zywork.beanNameLowerCase}{zywork.daoSuffix} = {zywork.beanNameLowerCase}{zywork.daoSuffix};
    }

    @PostConstruct
    public void init() {
        super.init({zywork.beanName}{zywork.doSuffix}.class, {zywork.beanName}{zywork.dtoSuffix}.class);
    }
}
