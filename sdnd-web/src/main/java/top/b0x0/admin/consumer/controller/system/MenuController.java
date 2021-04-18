package top.b0x0.admin.consumer.controller.system;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import me.zhengjie.annotation.Log;
//import me.zhengjie.exception.BadRequestException;
//import me.zhengjie.modules.system.domain.Menu;
//import me.zhengjie.modules.system.service.MenuService;
//import me.zhengjie.modules.system.service.dto.MenuDto;
//import me.zhengjie.modules.system.service.dto.MenuQueryCriteria;
//import me.zhengjie.modules.system.service.mapstruct.MenuMapper;
//import me.zhengjie.utils.PageUtil;
//import me.zhengjie.utils.SecurityUtils;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.validation.annotation.Validated;

//import javax.servlet.http.HttpServletResponse;
//import java.util.*;
//import java.util.stream.Collectors;

/**
 * @author Zheng Jie
 * @date 2018-12-03
 */

@RestController
@RequiredArgsConstructor
@Api(tags = "系统：菜单管理")
@RequestMapping("/api/menus")
public class MenuController {

//    private final MenuService menuService;
//    private final MenuMapper menuMapper;
//    private static final String ENTITY_NAME = "menu";
//
//    @ApiOperation("导出菜单数据")
//    @GetMapping(value = "/download")
//    @PreAuthorize("@el.check('menu:list')")
//    public void download(HttpServletResponse response, MenuQueryCriteria criteria) throws Exception {
//        menuService.download(menuService.queryAll(criteria, false), response);
//    }

    @GetMapping(value = "/build")
    @ApiOperation("获取前端所需菜单")
    public ResponseEntity<Object> buildMenus() {
//        List<MenuDto> menuDtoList = menuService.findByUser(SecurityUtils.getCurrentUserId());
//        List<MenuDto> menuDtos = menuService.buildTree(menuDtoList);
//        return new ResponseEntity<>(menuService.buildMenus(menuDtos),HttpStatus.OK);
        String buildMenu = "[{\"alwaysShow\":true,\"children\":[{\"component\":\"system/user/index\",\"hidden\":false,\"meta\":{\"icon\":\"peoples\",\"noCache\":true,\"title\":\"用户管理\"},\"name\":\"User\",\"path\":\"user\"},{\"component\":\"system/role/index\",\"hidden\":false,\"meta\":{\"icon\":\"role\",\"noCache\":true,\"title\":\"角色管理\"},\"name\":\"Role\",\"path\":\"role\"},{\"component\":\"system/menu/index\",\"hidden\":false,\"meta\":{\"icon\":\"menu\",\"noCache\":true,\"title\":\"菜单管理\"},\"name\":\"Menu\",\"path\":\"menu\"},{\"component\":\"system/dept/index\",\"hidden\":false,\"meta\":{\"icon\":\"dept\",\"noCache\":true,\"title\":\"部门管理\"},\"name\":\"Dept\",\"path\":\"dept\"},{\"component\":\"system/job/index\",\"hidden\":false,\"meta\":{\"icon\":\"Steve-Jobs\",\"noCache\":true,\"title\":\"岗位管理\"},\"name\":\"Job\",\"path\":\"job\"},{\"component\":\"system/dict/index\",\"hidden\":false,\"meta\":{\"icon\":\"dictionary\",\"noCache\":true,\"title\":\"字典管理\"},\"name\":\"Dict\",\"path\":\"dict\"},{\"component\":\"system/timing/index\",\"hidden\":false,\"meta\":{\"icon\":\"timing\",\"noCache\":true,\"title\":\"任务调度\"},\"name\":\"Timing\",\"path\":\"timing\"}],\"component\":\"Layout\",\"hidden\":false,\"meta\":{\"icon\":\"system\",\"noCache\":true,\"title\":\"系统管理\"},\"name\":\"系统管理\",\"path\":\"/system\",\"redirect\":\"noredirect\"},{\"alwaysShow\":true,\"children\":[{\"component\":\"monitor/online/index\",\"hidden\":false,\"meta\":{\"icon\":\"Steve-Jobs\",\"noCache\":true,\"title\":\"在线用户\"},\"name\":\"OnlineUser\",\"path\":\"online\"},{\"component\":\"monitor/log/index\",\"hidden\":false,\"meta\":{\"icon\":\"log\",\"noCache\":false,\"title\":\"操作日志\"},\"name\":\"Log\",\"path\":\"logs\"},{\"component\":\"monitor/log/errorLog\",\"hidden\":false,\"meta\":{\"icon\":\"error\",\"noCache\":true,\"title\":\"异常日志\"},\"name\":\"ErrorLog\",\"path\":\"errorLog\"},{\"component\":\"monitor/server/index\",\"hidden\":false,\"meta\":{\"icon\":\"codeConsole\",\"noCache\":true,\"title\":\"服务监控\"},\"name\":\"ServerMonitor\",\"path\":\"server\"},{\"component\":\"monitor/sql/index\",\"hidden\":true,\"meta\":{\"icon\":\"sqlMonitor\",\"noCache\":true,\"title\":\"SQL监控\"},\"name\":\"Sql\",\"path\":\"druid\"}],\"component\":\"Layout\",\"hidden\":false,\"meta\":{\"icon\":\"monitor\",\"noCache\":true,\"title\":\"系统监控\"},\"name\":\"系统监控\",\"path\":\"/monitor\",\"redirect\":\"noredirect\"},{\"alwaysShow\":true,\"children\":[{\"component\":\"mnt/server/index\",\"hidden\":false,\"meta\":{\"icon\":\"server\",\"noCache\":true,\"title\":\"服务器\"},\"name\":\"ServerDeploy\",\"path\":\"mnt/serverDeploy\"},{\"component\":\"mnt/app/index\",\"hidden\":false,\"meta\":{\"icon\":\"app\",\"noCache\":true,\"title\":\"应用管理\"},\"name\":\"App\",\"path\":\"mnt/app\"},{\"component\":\"mnt/deploy/index\",\"hidden\":false,\"meta\":{\"icon\":\"deploy\",\"noCache\":true,\"title\":\"部署管理\"},\"name\":\"Deploy\",\"path\":\"mnt/deploy\"},{\"component\":\"mnt/deployHistory/index\",\"hidden\":false,\"meta\":{\"icon\":\"backup\",\"noCache\":true,\"title\":\"部署备份\"},\"name\":\"DeployHistory\",\"path\":\"mnt/deployHistory\"},{\"component\":\"mnt/database/index\",\"hidden\":false,\"meta\":{\"icon\":\"database\",\"noCache\":true,\"title\":\"数据库管理\"},\"name\":\"Database\",\"path\":\"mnt/database\"}],\"component\":\"Layout\",\"hidden\":false,\"meta\":{\"icon\":\"mnt\",\"noCache\":true,\"title\":\"运维管理\"},\"name\":\"Mnt\",\"path\":\"/mnt\",\"redirect\":\"noredirect\"},{\"alwaysShow\":true,\"children\":[{\"component\":\"generator/index\",\"hidden\":false,\"meta\":{\"icon\":\"dev\",\"noCache\":false,\"title\":\"代码生成\"},\"name\":\"GeneratorIndex\",\"path\":\"generator\"},{\"component\":\"generator/config\",\"hidden\":true,\"meta\":{\"icon\":\"dev\",\"noCache\":false,\"title\":\"生成配置\"},\"name\":\"GeneratorConfig\",\"path\":\"generator/config/:tableName\"},{\"component\":\"tools/storage/index\",\"hidden\":false,\"meta\":{\"icon\":\"qiniu\",\"noCache\":true,\"title\":\"存储管理\"},\"name\":\"Storage\",\"path\":\"storage\"},{\"component\":\"tools/email/index\",\"hidden\":false,\"meta\":{\"icon\":\"email\",\"noCache\":true,\"title\":\"邮件工具\"},\"name\":\"Email\",\"path\":\"email\"},{\"component\":\"tools/swagger/index\",\"hidden\":true,\"meta\":{\"icon\":\"swagger\",\"noCache\":true,\"title\":\"接口文档\"},\"name\":\"Swagger\",\"path\":\"swagger2\"},{\"component\":\"tools/aliPay/index\",\"hidden\":false,\"meta\":{\"icon\":\"alipay\",\"noCache\":true,\"title\":\"支付宝工具\"},\"name\":\"AliPay\",\"path\":\"aliPay\"},{\"component\":\"generator/preview\",\"hidden\":true,\"meta\":{\"icon\":\"java\",\"noCache\":false,\"title\":\"生成预览\"},\"name\":\"Preview\",\"path\":\"generator/preview/:tableName\"}],\"component\":\"Layout\",\"hidden\":false,\"meta\":{\"icon\":\"sys-tools\",\"noCache\":true,\"title\":\"系统工具\"},\"name\":\"系统工具\",\"path\":\"/sys-tools\",\"redirect\":\"noredirect\"},{\"alwaysShow\":true,\"children\":[{\"component\":\"components/Echarts\",\"hidden\":false,\"meta\":{\"icon\":\"chart\",\"noCache\":false,\"title\":\"图表库\"},\"name\":\"Echarts\",\"path\":\"echarts\"},{\"component\":\"components/icons/index\",\"hidden\":false,\"meta\":{\"icon\":\"icon\",\"noCache\":true,\"title\":\"图标库\"},\"name\":\"Icons\",\"path\":\"icon\"},{\"component\":\"components/Editor\",\"hidden\":false,\"meta\":{\"icon\":\"fwb\",\"noCache\":true,\"title\":\"富文本\"},\"name\":\"Editor\",\"path\":\"tinymce\"},{\"component\":\"components/MarkDown\",\"hidden\":false,\"meta\":{\"icon\":\"markdown\",\"noCache\":true,\"title\":\"Markdown\"},\"name\":\"Markdown\",\"path\":\"markdown\"},{\"component\":\"components/YamlEdit\",\"hidden\":false,\"meta\":{\"icon\":\"dev\",\"noCache\":true,\"title\":\"Yaml编辑器\"},\"name\":\"YamlEdit\",\"path\":\"yaml\"}],\"component\":\"Layout\",\"hidden\":false,\"meta\":{\"icon\":\"zujian\",\"noCache\":true,\"title\":\"组件管理\"},\"name\":\"组件管理\",\"path\":\"/components\",\"redirect\":\"noredirect\"},{\"alwaysShow\":true,\"children\":[{\"alwaysShow\":true,\"children\":[{\"component\":\"nested/menu1/menu1-1\",\"hidden\":false,\"meta\":{\"icon\":\"menu\",\"noCache\":false,\"title\":\"三级菜单1\"},\"name\":\"Test\",\"path\":\"menu1-1\"},{\"component\":\"nested/menu1/menu1-2\",\"hidden\":false,\"meta\":{\"icon\":\"menu\",\"noCache\":true,\"title\":\"三级菜单2\"},\"name\":\"三级菜单2\",\"path\":\"menu1-2\"}],\"component\":\"ParentView\",\"hidden\":false,\"meta\":{\"icon\":\"menu\",\"noCache\":true,\"title\":\"二级菜单1\"},\"name\":\"二级菜单1\",\"path\":\"menu1\",\"redirect\":\"noredirect\"},{\"component\":\"nested/menu2/index\",\"hidden\":false,\"meta\":{\"icon\":\"menu\",\"noCache\":true,\"title\":\"二级菜单2\"},\"name\":\"二级菜单2\",\"path\":\"menu2\"}],\"component\":\"Layout\",\"hidden\":false,\"meta\":{\"icon\":\"menu\",\"noCache\":true,\"title\":\"多级菜单\"},\"name\":\"多级菜单\",\"path\":\"/nested\",\"redirect\":\"noredirect\"}]";
        return new ResponseEntity<>(buildMenu, HttpStatus.OK);
    }

//    @ApiOperation("返回全部的菜单")
//    @GetMapping(value = "/lazy")
//    @PreAuthorize("@el.check('menu:list','roles:list')")
//    public ResponseEntity<Object> query(@RequestParam Long pid){
//        return new ResponseEntity<>(menuService.getMenus(pid),HttpStatus.OK);
//    }
//
//    @ApiOperation("根据菜单ID返回所有子节点ID，包含自身ID")
//    @GetMapping(value = "/child")
//    @PreAuthorize("@el.check('menu:list','roles:list')")
//    public ResponseEntity<Object> child(@RequestParam Long id){
//        Set<Menu> menuSet = new HashSet<>();
//        List<MenuDto> menuList = menuService.getMenus(id);
//        menuSet.add(menuService.findOne(id));
//        menuSet = menuService.getChildMenus(menuMapper.toEntity(menuList), menuSet);
//        Set<Long> ids = menuSet.stream().map(Menu::getId).collect(Collectors.toSet());
//        return new ResponseEntity<>(ids,HttpStatus.OK);
//    }
//
//    @GetMapping
//    @ApiOperation("查询菜单")
//    @PreAuthorize("@el.check('menu:list')")
//    public ResponseEntity<Object> query(MenuQueryCriteria criteria) throws Exception {
//        List<MenuDto> menuDtoList = menuService.queryAll(criteria, true);
//        return new ResponseEntity<>(PageUtil.toPage(menuDtoList, menuDtoList.size()),HttpStatus.OK);
//    }
//
//    @ApiOperation("查询菜单:根据ID获取同级与上级数据")
//    @PostMapping("/superior")
//    @PreAuthorize("@el.check('menu:list')")
//    public ResponseEntity<Object> getSuperior(@RequestBody List<Long> ids) {
//        Set<MenuDto> menuDtos = new LinkedHashSet<>();
//        if(CollectionUtil.isNotEmpty(ids)){
//            for (Long id : ids) {
//                MenuDto menuDto = menuService.findById(id);
//                menuDtos.addAll(menuService.getSuperior(menuDto, new ArrayList<>()));
//            }
//            return new ResponseEntity<>(menuService.buildTree(new ArrayList<>(menuDtos)),HttpStatus.OK);
//        }
//        return new ResponseEntity<>(menuService.getMenus(null),HttpStatus.OK);
//    }
//
//    @Log("新增菜单")
//    @ApiOperation("新增菜单")
//    @PostMapping
//    @PreAuthorize("@el.check('menu:add')")
//    public ResponseEntity<Object> create(@Validated @RequestBody Menu resources){
//        if (resources.getId() != null) {
//            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
//        }
//        menuService.create(resources);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }
//
//    @Log("修改菜单")
//    @ApiOperation("修改菜单")
//    @PutMapping
//    @PreAuthorize("@el.check('menu:edit')")
//    public ResponseEntity<Object> update(@Validated(Menu.Update.class) @RequestBody Menu resources){
//        menuService.update(resources);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @Log("删除菜单")
//    @ApiOperation("删除菜单")
//    @DeleteMapping
//    @PreAuthorize("@el.check('menu:del')")
//    public ResponseEntity<Object> delete(@RequestBody Set<Long> ids){
//        Set<Menu> menuSet = new HashSet<>();
//        for (Long id : ids) {
//            List<MenuDto> menuList = menuService.getMenus(id);
//            menuSet.add(menuService.findOne(id));
//            menuSet = menuService.getChildMenus(menuMapper.toEntity(menuList), menuSet);
//        }
//        menuService.delete(menuSet);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
