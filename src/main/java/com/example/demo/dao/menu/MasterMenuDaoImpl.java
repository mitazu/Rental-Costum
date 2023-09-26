package com.example.demo.dao.menu;

import com.example.demo.configuration.DBQueryHandler;
import com.example.demo.configuration.Response;
import com.example.demo.util.ObjectMapper;
import com.example.demo.util.jwt.JwtTokenResponse;
import com.example.demo.util.jwt.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class MasterMenuDaoImpl extends DBQueryHandler implements MasterMenuDao {

    public MasterMenuDaoImpl(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    @Override
    public ResponseEntity<?> getMenuJson(HttpServletRequest request) {
        Connection con = Connect();
        try {
            JwtTokenResponse jwtResp = SessionUtil.getUserData(request);
            List<LinkedHashMap<String, Object>> listMenu = Select(con, "select \n" +
                    "\tmm.menu_id,\n" +
                    "\tmm.menu_name,\n" +
                    "\tmm.menu_icon as icon,\n" +
                    "\tmm.path_url as \"path\",\n" +
                    "\tmm.parent_id,\n" +
                    "\tmm.sequence_key as \"sequence\",\n" +
                    "\tmrm.role_id as role_id,\n" +
                    "\tmm.menu_active\n" +
                    "from master_menu mm \n" +
                    "inner join master_role_menu mrm\n" +
                    "\ton mm.menu_id = mrm.menu_id\n" +
                    "where mrm.role_id = " + Integer.valueOf(jwtResp.getUser_role()) + "\n" +
                    "\tand mm.menu_active = 1\n" +
                    "order by mm.sequence_key asc", new ObjectMapper());
            System.out.println("test1");
            System.out.println(listMenu);
            List<LinkedHashMap<String, Object>> listMenuResp = new ArrayList<>();
            listMenu.stream().collect(Collectors.groupingBy(x -> x.get("parent_id"))).forEach((k, v) -> {
                v.forEach(x -> {
                    System.out.println("cek1"+x.get("parent_id"));
                    if (x.get("parent_id").toString().equals("0")) {
                        System.out.println("cekkk");
                        LinkedHashMap<String, Object> menuResp = new LinkedHashMap<>();
                        menuResp.put("menu_id", x.get("menu_id"));
                        menuResp.put("menu_name", x.get("menu_name"));
                        menuResp.put("icon", x.get("icon"));
                        menuResp.put("href", x.get("path"));
                        menuResp.put("sequence", x.get("sequence"));
                        List<LinkedHashMap<String, Object>> listChildMenu = listMenu.stream().filter(y -> y.get("parent_id").equals(x.get("menu_id"))).collect(Collectors.toList());
                        List<LinkedHashMap<String, Object>> listChildMenuResp = new ArrayList<>();
                        listChildMenu.forEach(y -> {
                            LinkedHashMap<String, Object> childMenuResp = new LinkedHashMap<>();
                            childMenuResp.put("menu_id", y.get("menu_id"));
                            childMenuResp.put("menu_name", y.get("menu_name"));
                            childMenuResp.put("path", y.get("path"));
                            childMenuResp.put("sequence", y.get("sequence"));
                            listChildMenuResp.add(childMenuResp);
                            System.out.println("test2");
                            System.out.println(listChildMenuResp);
                        });
                        if (listChildMenu.size() > 0) {
                            menuResp.put("childs", listChildMenuResp);
                        } else {
                            menuResp.put("childs", new ArrayList<>());
                        }
                        listMenuResp.add(menuResp);
                        System.out.println("test3");
                        System.out.println(listMenuResp);
                    }
                });
            });

            Commit(con);
            return Response.response(listMenuResp, listMenuResp.size(), HttpStatus.OK);
        } catch (Exception e) {
            Rollback(con);
            return Response.response(e.getMessage(), HttpStatus.BAD_REQUEST);
        } finally {
            Close(con);
        }
    }

}
