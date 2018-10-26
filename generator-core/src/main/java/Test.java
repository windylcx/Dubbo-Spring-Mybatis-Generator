import com.chunxiao.dev.config.DtoConfig;
import com.chunxiao.dev.config.DtoUtilConfig;
import com.chunxiao.dev.generator.project.DtoGenerator;
import com.chunxiao.dev.pojo.TableInfo;
import com.chunxiao.dev.util.ObjectConvertUtil;

/**
 * Created by chunxiaoli on 12/22/16.
 */
public class Test {
    public static void main(String args[]){
        TableInfo t1=new TableInfo();
        t1.setTableName("a");
        t1.setDomainName("123");
        TableInfo t2=new TableInfo();
        t2.setTableName("b");

        TableInfo t3= (TableInfo) ObjectConvertUtil.merge(t1,t2);

        System.out.println(t3.getTableName());

    }

    public static void testJson2Pojo() {
        DtoConfig dtoConfig = new DtoConfig();
        dtoConfig.setDir("src/main/java");
        dtoConfig.setPackageName("com.chunxiao.onecollection");
        dtoConfig.setJsonConfigPath("test.json");
        new DtoGenerator(dtoConfig).generate();
    }

    public static void testPojo2Dto() {
        DtoConfig dtoConfig = new DtoConfig();
        dtoConfig.setDir("src/main/java");
        dtoConfig.setPackageName("com.chunxiao.onecollection");
        dtoConfig.setJsonConfigPath("test.json");
        String dir = "task-rpc/task-provider/src/main/java";
        String className = "com.chunxiao.onecollection.task.provider.pojo.Task";
        new DtoGenerator(dtoConfig).generateFromPojoClassFile(dir, className);
    }


    public static void testDtoUtil(){
        String dir = "task-rpc/task-provider/src/main/java";
        String className = "com.chunxiao.onecollection.task.provider.pojo.Task";
        String packageName = "com.chunxiao.onecollection.task.provider.dto";
        DtoUtilConfig dtoUtilConfig=new DtoUtilConfig();
        dtoUtilConfig.setOutDir(dir);
        dtoUtilConfig.setClassName(className);
        dtoUtilConfig.setPackageName(packageName);
    }


}
