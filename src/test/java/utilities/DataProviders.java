package utilities;

import org.testng.annotations.DataProvider;

import java.io.IOException;

public class DataProviders {

    //Data Provider 1
    @DataProvider(name = "dp1")
    String[][] getData() throws IOException {
        String path=".\\testData\\guru test data.xlsx"; //taking excel data from testData folder
//        String path=System.getProperty("user.dir")+"\\testData\\guru tst data.xlsx";
        ExcelUtility ex= new ExcelUtility(path); // creating object for Excel utility

        int totalrows=ex.rowCount("Sheet1");
        int totalcells=  ex.cellCount("Sheet1",1);
        String[][] loginData = new String[totalrows][totalcells];//created 2d array which can store row and cell count

        // reading data from excel
        // i is for rows and j for columns
        for(int i=1;i<=totalrows;i++)
        {
            for(int j=0;j<totalcells;j++)
            {
                loginData[i-1][j]=ex.cellData("Sheet1",i,j); // 1 0
            }
        }
        return loginData;// returning 2 d array

    }

    // Data Provider 2
    //Data Provider 3

}
