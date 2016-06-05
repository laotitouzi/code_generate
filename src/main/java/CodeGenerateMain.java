import codeGenerate.def.FtlDef;
import codeGenerate.factory.CodeGenerateFactory;

/**
 * @Author Han, Tixiang
 * @Create 2016/5/29
 */
public class CodeGenerateMain {

    public static void main(String [] args){
        String tableName="category"; //
        String codeName ="分类";
        String entityPackage ="category";
        String keyType = FtlDef.KEY_TYPE_02;
        CodeGenerateFactory.codeGenerate(tableName, codeName,entityPackage,keyType);
    }
}
