import java.util.ArrayList;
/**
 * Created by Dondelium on 7/13/2016.
 */
public class BlockComponent{
    public ArrayList<String> componentNames = new ArrayList<String>();
    public ArrayList<Integer> componentAmounts = new ArrayList<Integer>();
    public BlockComponent(ArrayList<String> inCompNames, ArrayList<Integer> inCompAmts){
        for (String name : inCompNames)
            componentNames.add(name);
        for (Integer num : inCompAmts)
            componentAmounts.add(num);
    }
}
