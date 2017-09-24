package cn.crap.service.imp.tool;

import java.util.List;

import cn.crap.service.mybatis.custom.CustomErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.crap.dto.LoginInfoDto;
import cn.crap.dto.PickDto;
import cn.crap.enumeration.InterfaceStatus;
import cn.crap.enumeration.MonitorType;
import cn.crap.enumeration.RequestMethod;
import cn.crap.enumeration.TrueOrFalse;
import cn.crap.framework.MyException;
import cn.crap.service.IModuleService;
import cn.crap.service.IRoleService;
import cn.crap.service.ICacheService;
import cn.crap.service.IPickService;
import cn.crap.model.mybatis.Error;

/**
 * 下拉选着
 * @author Ehsan
 *
 */
@Service("pickService")
public class PickService implements IPickService{
	@Autowired
	private ICacheService cacheService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IModuleService moduleService;
	@Autowired
	private CustomErrorService customErrorService;

	@Override
	public void getPickList(List<PickDto> picks, String code, String key, LoginInfoDto user) throws MyException {
		PickDto pick = null;
		switch (code) {
//			case "RECOMMENDPROJECT": // 推荐的模块
//				for (Project p : projectService.findByMap(Tools.getMap("type", ProjectType.RECOMMEND.getType() ), null, null)) {
//					pick = new PickDto(p.getId(), p.getName());
//					picks.add(pick);
//				}
//				return;
			case "REQUESTMETHOD": // 枚举 请求方式 post get
				for (RequestMethod status : RequestMethod.values()) {
					pick = new PickDto(status.name(), status.getName(), status.getName());
					picks.add(pick);
				}
				return;
				// 枚举 接口状态
			case "INTERFACESTATUS":
				for (InterfaceStatus status : InterfaceStatus.values()) {
					pick = new PickDto(status.getName(), status.name());
					picks.add(pick);
				}
				return;
			case "TRUEORFALSE":// 枚举true or false
				for (TrueOrFalse status : TrueOrFalse.values()) {
					pick = new PickDto(status.getName(), status.name());
					picks.add(pick);
				}
				return;
			case "MONITORTYPE":// 监控类型
				for (MonitorType monitorType : MonitorType.values()) {
					pick = new PickDto(monitorType.name(), monitorType.getValue()+"", monitorType.getName());
					picks.add(pick);
				}
				return;
			case "ERRORCODE":// 错误码
				for (Error error : customErrorService.queryByProjectId(key)) {
					pick = new PickDto(error.getErrorCode(), error.getErrorCode() + "--" + error.getErrorMsg());
					picks.add(pick);
				}
				return;
		}
	}

}
