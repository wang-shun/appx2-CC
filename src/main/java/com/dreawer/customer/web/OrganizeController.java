package com.dreawer.customer.web;

import com.dreawer.customer.domain.Organize;
import com.dreawer.customer.lang.UserStatus;
import com.dreawer.customer.service.OrganizeService;
import com.dreawer.customer.web.form.AddOrganizeForm;
import com.dreawer.responsecode.rcdt.Error;
import com.dreawer.responsecode.rcdt.ResponseCode;
import com.dreawer.responsecode.rcdt.ResponseCodeRepository;
import com.dreawer.responsecode.rcdt.Success;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class OrganizeController {
	
    @Autowired
    private OrganizeService organizeService; // 组织信息服务
	
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 添加组织。
     * @param req 用户请求。
     * @param form 添加组织表单。
     * @param result 表单校验结果。
     * @return 如果组织已存在，返回组织id。如果不存在，添加新组织并返回id。
     */
    @RequestMapping(value="/orgaize/add", method=RequestMethod.POST)
    public ResponseCode addOrganize(HttpServletRequest req,
    		@RequestBody @Valid AddOrganizeForm form, BindingResult result) {
    	if (result.hasErrors()) {
            return ResponseCodeRepository.fetch(result.getFieldError().getDefaultMessage(), result.getFieldError().getField(), Error.ENTRY);
        }
    	try {
    		Organize organize = organizeService.findOrganizeByAppId(form.getAppId());
    		if(organize==null) {
    			organize = new Organize();
    			organize.setAppId(form.getAppId());
    			organize.setName(form.getName());
    			organize.setCategory(form.getCategory());
    			organize.setStatus(UserStatus.ACTIVATED);
    			organizeService.save(organize);
    		}
        	return Success.SUCCESS(organize.getId());
		} catch (Exception e) {
	    	logger.error("error",e);
		    return Error.APPSERVER;
		}
    }
	
}
