import java.util.ArrayList;
import java.util.HashMap;
/**
 * Created by Dondelium on 7/13/2016.
 */
public class BlockDefinitions{
    public HashMap<String, BlockComponent> blocks = new HashMap<String, BlockComponent>();
    public BlockDefinitions(){
        generateDefintions();
    }

    private void generateDefintions(){
        ArrayList<String> tempNames = new ArrayList<String>();
        ArrayList<Integer> tempNums = new ArrayList<Integer>();

        tempNames.add("SteelPlate"); tempNums.add(25);
        blocks.put("LargeBlockArmorBlock", new BlockComponent(tempNames, tempNums));
        tempNames.clear(); tempNums.clear();

        tempNames.add("SteelPlate"); tempNums.add(13);
        blocks.put("LargeBlockArmorCorner", new BlockComponent(tempNames, tempNums));
        tempNames.clear(); tempNums.clear();

        tempNames.add("SteelPlate"); tempNums.add(4);
        blocks.put("LargeBlockArmorSlope", new BlockComponent(tempNames, tempNums));
        tempNames.clear(); tempNums.clear();

        tempNames.add("SteelPlate"); tempNums.add(21);
        blocks.put("LargeBlockArmorCornerInv", new BlockComponent(tempNames, tempNums));
        tempNames.clear(); tempNums.clear();

        tempNames.add("SteelPlate"); tempNums.add(13);
        blocks.put("LargeRoundArmor_Slope", new BlockComponent(tempNames, tempNums));
        tempNames.clear(); tempNums.clear();

        tempNames.add("SteelPlate"); tempNums.add(4);
        blocks.put("LargeRoundArmor_Corner", new BlockComponent(tempNames, tempNums));
        tempNames.clear(); tempNums.clear();

        tempNames.add("SteelPlate"); tempNums.add(21);
        blocks.put("LargeRoundArmor_CornerInv", new BlockComponent(tempNames, tempNums));
        tempNames.clear(); tempNums.clear();

        tempNames.add("SteelPlate"); tempNums.add(150);
        tempNames.add("MetalGrid"); tempNums.add(50);
        blocks.put("LargeHeavyBlockArmorBlock", new BlockComponent(tempNames, tempNums));
        tempNames.clear(); tempNums.clear();

        tempNames.add("SteelPlate"); tempNums.add(75);
        tempNames.add("MetalGrid"); tempNums.add(25);
        blocks.put("LargeHeavyBlockArmorSlope", new BlockComponent(tempNames, tempNums));
        tempNames.clear(); tempNums.clear();

        tempNames.add("SteelPlate"); tempNums.add(25);
        tempNames.add("MetalGrid"); tempNums.add(10);
        blocks.put("LargeHeavyBlockArmorCorner", new BlockComponent(tempNames, tempNums));
        tempNames.clear(); tempNums.clear();

        tempNames.add("SteelPlate"); tempNums.add(125);
        tempNames.add("MetalGrid"); tempNums.add(50);
        blocks.put("LargeHeavyBlockArmorCornerInv", new BlockComponent(tempNames, tempNums));
        tempNames.clear(); tempNums.clear();

        tempNames.add("SteelPlate"); tempNums.add(75);
        tempNames.add("MetalGrid"); tempNums.add(25);
        blocks.put("LargeHeavyBlockArmorAngledSlope", new BlockComponent(tempNames, tempNums));
        tempNames.clear(); tempNums.clear();

        tempNames.add("SteelPlate"); tempNums.add(30);
        tempNames.add("MetalGrid"); tempNums.add(8);
        blocks.put("LargeHeavyBlockArmorAngledCorner", new BlockComponent(tempNames, tempNums));
        tempNames.clear(); tempNums.clear();

        tempNames.add("SteelPlate"); tempNums.add(19);
        blocks.put("LargeBlockArmorSlope2Base", new BlockComponent(tempNames, tempNums));
        tempNames.clear(); tempNums.clear();

        tempNames.add("SteelPlate"); tempNums.add(7);
        blocks.put("LargeBlockArmorSlope2Tip", new BlockComponent(tempNames, tempNums));
        tempNames.clear(); tempNums.clear();

        tempNames.add("SteelPlate"); tempNums.add(10);
        blocks.put("LargeBlockArmorCorner2Base", new BlockComponent(tempNames, tempNums));
        tempNames.clear(); tempNums.clear();

        tempNames.add("SteelPlate"); tempNums.add(4);
        blocks.put("LargeBlockArmorCorner2Tip", new BlockComponent(tempNames, tempNums));
        tempNames.clear(); tempNums.clear();

        tempNames.add("SteelPlate"); tempNums.add(22);
        blocks.put("LargeBlockArmorInvCorner2Base", new BlockComponent(tempNames, tempNums));
        tempNames.clear(); tempNums.clear();

        tempNames.add("SteelPlate"); tempNums.add(16);
        blocks.put("LargeBlockArmorInvCorner2Tip", new BlockComponent(tempNames, tempNums));
        tempNames.clear(); tempNums.clear();

        tempNames.add("SteelPlate"); tempNums.add(112);
        tempNames.add("MetalGrid"); tempNums.add(45);
        blocks.put("LargeHeavyBlockArmorSlope2Base", new BlockComponent(tempNames, tempNums));
        tempNames.clear(); tempNums.clear();

        tempNames.add("SteelPlate"); tempNums.add(35);
        tempNames.add("MetalGrid"); tempNums.add(6);
        blocks.put("LargeHeavyBlockArmorSlope2Tip", new BlockComponent(tempNames, tempNums));
        tempNames.clear(); tempNums.clear();

        tempNames.add("SteelPlate"); tempNums.add(55);
        tempNames.add("MetalGrid"); tempNums.add(15);
        blocks.put("LargeHeavyBlockArmorCorner2Base", new BlockComponent(tempNames, tempNums));
        tempNames.clear(); tempNums.clear();

        tempNames.add("SteelPlate"); tempNums.add(19);
        tempNames.add("MetalGrid"); tempNums.add(6);
        blocks.put("LargeHeavyBlockArmorCorner2Tip", new BlockComponent(tempNames, tempNums));
        tempNames.clear(); tempNums.clear();

        tempNames.add("SteelPlate"); tempNums.add(133);
        tempNames.add("MetalGrid"); tempNums.add(45);
        blocks.put("LargeHeavyBlockArmorInvCorner2Base", new BlockComponent(tempNames, tempNums));
        tempNames.clear(); tempNums.clear();

        tempNames.add("SteelPlate"); tempNums.add(94);
        tempNames.add("MetalGrid"); tempNums.add(25);
        blocks.put("LargeHeavyBlockArmorInvCorner2Tip", new BlockComponent(tempNames, tempNums));
        tempNames.clear(); tempNums.clear();

        tempNames.add("SteelPlate"); tempNums.add(20);
        tempNames.add("Construction"); tempNums.add(10);
        tempNames.add("PowerCell"); tempNums.add(120);
        blocks.put("LargeBlockBatteryBlock", new BlockComponent(tempNames, tempNums));
        tempNames.clear(); tempNums.clear();
    }
}