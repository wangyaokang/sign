package com.wyk.framework.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import com.wyk.framework.entity.AutoIdEntity;
import com.wyk.framework.page.Paginator;
import com.wyk.framework.service.BaseService;

/**
 * @author wyk
 *
 */
public abstract class BaseController<T> implements WebxController {

	protected final Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	protected BaseService<T> service;
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public @ResponseBody T qet(@RequestParam Map<String, Object> params) {
		return service.get(params);
	}
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody List<T> list(@RequestParam Map<String, Object> params) {
		return service.query(params);
	}
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public @ResponseBody List<T> listPost(@RequestParam Map<String, Object> params) {
		return service.query(params);
	}
	
	/**
	 * 
	 * @param params
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> query(@RequestParam Map<String, Object> params, @RequestParam(value="page", required=false) Integer page, @RequestParam(value="rows", required=false) Integer rows) {
		Map<String, Object> result = new HashMap<String, Object>();

		if (page == null) {
			page = Paginator.DEFAULT_CURRENT_PAGE;
		}
		if (rows == null) {
			rows = Paginator.DEFAULT_PAGE_SIZE;
		}
		Paginator paginator = new Paginator(page, rows);
		List<T> list = service.query(params, paginator);
		
		result.put("total", paginator.getTotalCount());
		result.put("rows", list);
		
		return result;
	}
	
	/**
	 * 
	 * @param params
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> queryPost(@RequestParam Map<String, Object> params, @RequestParam(value="page", required=false) Integer page, @RequestParam(value="rows", required=false) Integer rows) {
		return query(params, page, rows);
	}
	
	/**
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> save(@ModelAttribute T entity) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		if (!(entity instanceof AutoIdEntity)) {
			throw new IllegalArgumentException("save(entity) must be instace of AutoIdEntity");
		}
		
		try {
			AutoIdEntity baseEntity = (AutoIdEntity) entity;
			if (baseEntity.getId() == null || baseEntity.getId() == 0) {
				logger.debug("entity.id 为空，新增");
				service.insert(entity);
			} else {
				logger.debug("entity.id = " + baseEntity.getId() + ", 修改");
				service.update(entity);
			}
			result.put(RETURN_CODE, SUCCESS);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			result.put(RETURN_CODE, ERROR_UNKNOWN);
			result.put(MSG, ex.getMessage());
		}
		return result;
	}
	
	/**
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> insert(@ModelAttribute T entity) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			service.insert(entity);
			result.put(RETURN_CODE, SUCCESS);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			result.put(RETURN_CODE, ERROR_UNKNOWN);
			result.put(MSG, ex.getMessage());
		}
		return result;
	}
	
	/**
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> update(@ModelAttribute T entity) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			service.update(entity);
			result.put(RETURN_CODE, SUCCESS);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			result.put(RETURN_CODE, ERROR_UNKNOWN);
			result.put(MSG, ex.getMessage());
		}
		return result;
	}
	
	/**
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> delete(@ModelAttribute T entity) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			service.delete(entity);
			result.put(RETURN_CODE, SUCCESS);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			result.put(RETURN_CODE, ERROR_UNKNOWN);
			result.put(MSG, ex.getMessage());
		}
		return result;
	}
	
	/**
	 * 
	 * @return HttpServletRequest
	 */
	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	/**
	 * 
	 * @return HttpServletResponse
	 */
	public static HttpServletResponse getResponse() {
		return ((ServletWebRequest)RequestContextHolder.getRequestAttributes()).getResponse();
	}
}
