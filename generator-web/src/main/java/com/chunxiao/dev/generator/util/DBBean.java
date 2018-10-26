package com.chunxiao.dev.generator.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


/**
 * Created by chunxiaoli on 7/6/17.
 */
@Component
public class DBBean {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public  void execute(String sql){

        jdbcTemplate.execute(sql);

        /*ScriptRunner runner = null;
        try {
            runner = new ScriptRunner(dataSource.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        runner.setAutoCommit(true);
        runner.setStopOnError(true);
        Reader reader = new StringReader(sql);
        runner.runScript(reader);
        runner.closeConnection();*/

    }
}
