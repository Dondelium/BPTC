import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;
import java.util.Map;

/**
 * Created by Dondelium on 7/13/2016.
 */
public class MainForm {
    private JTextField bpName;
    public JButton selectBP;
    private JTextArea bpCompsOutput;
    private JButton exitButton;
    private JPanel mainPanel;
    private JTextArea defOutput;
    private JTextField defName;
    private JButton selectDef;
    private JRadioButton radioVanilla;  //Figured it might be nice to keep everything in one form for now.
    private JRadioButton radioMod;
    private JRadioButton radioLocal;
    private JButton defLoadButton;
    private JButton defsSaveButton;
    private JButton defsClearButton;
    private JCheckBox cbExtract;
    private JCheckBox cbLoad;
    private JFrame parent;
    private BlockDefinitions blockDefs;
    private ButtonGroup group = new ButtonGroup();

    private String vanillaPath = "C:\\Program Files\\Steam\\steamapps\\common\\SpaceEngineers\\Content\\Data\\";
    private String modPath = System.getProperty("user.home")+"\\AppData\\Roaming\\SpaceEngineers\\Mods\\"; //Default paths.

    public MainForm() {
        parent = (JFrame)SwingUtilities.getWindowAncestor(mainPanel);
        blockDefs = new BlockDefinitions();

        group.add(radioVanilla);
        group.add(radioMod); //Radio buttons for file selection.
        group.add(radioLocal);

        setConfigure();

        //Implament Auto-load/extract calls depending on settings.
        if(cbExtract.isSelected()){
            defOutput.append("Auto-Extracting vanilla definitions.\n");
            try{
                File file = new File(vanillaPath+"\\CubeBlocks.sbc");
                extractDefinitions(file);
                defOutput.append(blockDefs.blocks.size()+" definitions successfully loaded.\n");
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        if(cbLoad.isSelected()){
            loadDefinitions();
        }

        selectBP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                File out = runFileDialog(bpName);
                if(out != null){
                    bpCompsOutput.setText("");
                    bpCompsOutput.append("Processing file:\n");
                    processBPFile(out);
                }
            }
        });
        selectDef.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) { //Check the radio buttons, and then make a decision.
                if(radioVanilla.isSelected()) {
                    String out = runFolderDialog(defName, "Select SE Data folder location");
                    if (out != null) {
                        defOutput.append("Data folder selected: " + out + "\n");
                        vanillaPath = out;
                    }
                } else if(radioMod.isSelected()){
                    String out = runFolderDialog(defName, "Select SE Mod folder location");
                    if (out != null) {
                        defOutput.append("Mod folder selected: " + out + "\n");
                        modPath = out;
                    }
                } else if(radioLocal.isSelected()) {
                    File out = runFileDialog(bpName);
                    if(out != null){
                        defOutput.append("File selected: " + out + "\n");
                        extractDefinitions(out);
                    }
                } else {
                    JOptionPane.showMessageDialog(parent, "Please select the type of definition you want to load.");
                }
            }
        });
        exitButton.addActionListener(new ActionListener() { //Exit button.
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int reply = JOptionPane.showConfirmDialog(null, "Are you sure you wish to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    saveConfigure(); //I need to put in an intercept with this for when the user presses the x button.
                    System.exit(0);
                }
            }
        });
        defLoadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                defOutput.setText("");
                defOutput.append("Clearing definitions from memory.\n");
                blockDefs.blocks.clear();
                loadDefinitions();
            }
        });
        defsSaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                defOutput.append("Attempting to save " + blockDefs.blocks.size() + "definitions to file.\n");
                saveDefinitions();
            }
        });
        defsClearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                defOutput.setText("");
                defOutput.append("Clearing definitions from memory.\n");
                blockDefs.blocks.clear();
                defOutput.append(blockDefs.blocks.size()+" block definitions loaded.\n");
            }
        });
    }


    private void processBPFile(File in){
        ArrayList<String> blockNames = new ArrayList<String>();
        String line = "";

        try {
            BufferedReader br = openFile(in);
            while ((line = br.readLine()) != null) {
                if (line.contains("<SubtypeName>")) {
                    int index1 = line.indexOf(">");
                    int index2 = line.indexOf("<", index1);
                    line = line.substring(index1 + 1, index2);
                    blockNames.add(line);
                }
            }
            br.close();
        } catch(Exception e){
            return;
        }

        ArrayList<String> componentNames = new ArrayList<String>();
        ArrayList<Integer> componentNums = new ArrayList<Integer>();
        int failedBlocks = 0;
        for(String item : blockNames){
            BlockComponent refBlock = blockDefs.blocks.get(item);
            if (refBlock != null){
                for(int i = 0; i < refBlock.componentNames.size(); i++){
                    if (componentNames.contains(refBlock.componentNames.get(i))) {
                        int index = componentNames.indexOf(refBlock.componentNames.get(i));
                        int firstVal = componentNums.get(index) + refBlock.componentAmounts.get(i);//what the crap Java... why do I need to do this?
                        componentNums.set(index, firstVal);
                    } else {
                        componentNames.add(refBlock.componentNames.get(i));
                        componentNums.add(refBlock.componentAmounts.get(i));
                    }
                }
            }
            else
                failedBlocks++;
        }
        bpCompsOutput.append("File processed.\n");
        bpCompsOutput.append(blockNames.size() + " blocks were processed.\n");
        if (failedBlocks >= 1)
            bpCompsOutput.append("Definitions were not found for " + failedBlocks + " blocks.\n");
        else
            bpCompsOutput.append("Definitions were found for all blocks.\n");
        bpCompsOutput.append("\n");
        bpCompsOutput.append("Components needed for blueprint:\n");

        for (int i = 0; i < componentNames.size(); i++) {
            bpCompsOutput.append(componentNames.get(i) + ": " + componentNums.get(i) + "\n");
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainForm");
        frame.setContentPane(new MainForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void setConfigure(){
        try {
            String current = new java.io.File(".").getCanonicalPath();
            current = current + "\\bin\\Setting.cfg";
            BufferedReader br = openFile(current);
            String line = "";
            defOutput.append("Loading configuration.\n");
            while((line = br.readLine()) != null){
                if(line.contains("VanillaPath"))
                    vanillaPath = line.substring(line.indexOf(" ")+1);
                else if(line.contains("ModPath"))
                    modPath = line.substring(line.indexOf(" ")+1);
                else if(line.contains("Extract")){
                    line = line.substring(line.indexOf(" ")+1);
                    if(line.equals("false"))
                        cbExtract.setSelected(false);
                } else if(line.contains("Load")){
                    line = line.substring(line.indexOf(" ")+1);
                    if(line.equals("true"))
                        cbLoad.setSelected(true);
                }
            }
            br.close();
        } catch(IOException e){
            defOutput.append("Unable to load configure file, loading defaults.");
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private void saveConfigure(){
        Writer writer = null;
        try{
            String current = new java.io.File(".").getCanonicalPath();
            current = current + "\\bin\\Setting.cfg";
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(current), "utf-8"));
            writer.write("VanillaPath "+vanillaPath+"\n");
            writer.write("ModPath "+modPath+"\n");
            writer.write("Extract " +cbExtract.isSelected() + "\n");
            writer.write("Load " +cbLoad.isSelected() + "\n");
        } catch(Exception e){
            //Were closing it anyway.
        } finally {
            try {writer.close();} catch (Exception e) {} //Neat way of closing files I found looking up the java writer, so I am using it now.
        }
    }

    private void loadDefinitions(){
        defOutput.append("Attempting to load definitions from save file:\n");
        try {
            String current = new java.io.File(".").getCanonicalPath();
            current = current + "\\bin\\Definitions.txt";
            BufferedReader br = openFile(current);
            String line = "";
            String name = "";
            ArrayList<String> tempNames = new ArrayList<String>();
            ArrayList<Integer> tempNums = new ArrayList<Integer>();
            while ((line = br.readLine()) != null) {
                if (line.contains("BPTCName")) { //I use very customized keywords because there is no telling what a modder may name their blocks...
                    if(name != "")//We do a once through and save system here, harder to keep up with but efficient.
                        blockDefs.blocks.put(name, new BlockComponent(tempNames, tempNums));
                    name = line.substring(line.indexOf(" ")+1);
                    tempNames.clear();
                    tempNums.clear();
                } else if(line.contains("BPTCComponent"))
                    tempNames.add(line.substring(line.indexOf(" ")+1));
                else if(line.contains("BPTCAmount")){
                    try {
                        int num = Integer.parseInt(line.substring(line.indexOf(" ") + 1));
                        tempNums.add(num);
                    } catch(Exception e){/*Doing nothing*/} //I put this here because there may be errors converting string to Integer.
                }
            }
            br.close(); //Best to close this before moving on to anything else.
            if(name != "")
                blockDefs.blocks.put(name, new BlockComponent(tempNames, tempNums));

            defOutput.append(blockDefs.blocks.size() + " Definitions successfully written to save file.");
        } catch(IOException e){
            defOutput.append("No save file was found, 0 definitions loaded.\n");
        } catch(Exception e){ //Always good for error detection, on user side, and programmers side as well.
            defOutput.append("There was an error reading the save file.\n");
            e.printStackTrace(); //I should really build a log file output here.
        }
    }

    private void saveDefinitions(){
        Writer writer = null;
        try {
            String current = new java.io.File(".").getCanonicalPath();
            current = current + "\\bin\\Definitions.txt";
            defOutput.append("Saving file to: " + current + "\n");
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(current), "utf-8"));

            Map<String, BlockComponent> map = blockDefs.blocks;
            for(Map.Entry<String, BlockComponent> entry : map.entrySet()){
                writer.write("BPTCName "+entry.getKey());
                BlockComponent comp = entry.getValue();
                for(int i = 0; i < comp.componentNames.size(); i++){
                    writer.write("\nBPTCComponent " + comp.componentNames.get(i));
                    writer.write("\nBPTCAmount "+comp.componentAmounts.get(i));
                }
                writer.write("\n");
            }

            defOutput.append(blockDefs.blocks.size() + " definitions saved to file.\n");
        } catch(IOException e){
            defOutput.append("Was not able to open file for writing.\n");
            defOutput.append(e.toString());
        } catch(Exception e){ //More error detection.
            defOutput.append("An error occurred while saving file.\n");
            e.printStackTrace(); //I should really build a log file output here.
        } finally {
            try {writer.close();} catch(Exception e){} //Neat way of closing files I found looking up the java writer, so I am using it now.
        }
    }

    private void extractDefinitions(File in){
        try {
            BufferedReader br = openFile(in);
            String line = "";
            String name = "";
            ArrayList<String> tempNames = new ArrayList<String>();
            ArrayList<Integer> tempNums = new ArrayList<Integer>();
            while ((line = br.readLine()) != null) {
                if (line.contains("<SubtypeId>")) {
                    if (name != "") {
                        blockDefs.addDefinition(name, tempNames, tempNums);
                        tempNames.clear();
                        tempNums.clear();
                    }
                    int index1 = line.indexOf(">");
                    int index2 = line.indexOf("<", index1);
                    name = line.substring(index1 + 1, index2);
                } else if (line.contains("<Component Subtype")) {
                    int index1 = line.indexOf("\"");
                    int index2 = line.indexOf("\"", index1+1);
                    int index3 = line.indexOf("\"", index2+1);
                    int index4 = line.indexOf("\"", index3+1);

                    tempNames.add(line.substring(index1 + 1, index2));
                    int num = Integer.parseInt(line.substring(index3 + 1, index4));
                    tempNums.add(num);
                }
            }
            if (name != "") {
                blockDefs.addDefinition(name, tempNames, tempNums);
                tempNames.clear();
                tempNums.clear();
            }
        } catch(IOException e){
            defOutput.append(in + " was not able to be extracted.");
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    //-----------------------------------------------------------------------------------------------------
    //-----------------------------------------Function library--------------------------------------------
    //-----------------------------------------------------------------------------------------------------

    //So I found out real quickly that JFileChooser is far more versatile than FileDialog, but alas, FileDialog looks nicer, so I'm still using it for the single files.
    private File runFileDialog(JTextField outputField){
        FileDialog fd = new FileDialog(parent, "Open File", FileDialog.LOAD);
        fd.setVisible(true);
        if(fd.getFiles().length > 0){
            File out = fd.getFiles()[0];
            String path = fd.getDirectory() + fd.getFile();
            outputField.setText(path);
            return out;
        } else {
            return null;
        }
    }

    private String runFolderDialog(JTextField outputField, String name){
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new java.io.File("."));
        fc.setDialogTitle(name);
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setAcceptAllFileFilterUsed(false);
        if (fc.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile()+"";
        } else {
            return null;
        }
    }

    //I didn't like all the extra stuff Java made me do for files, so I built my own functions to remove those issues.
    //This would be better in its own library, but the project isn't big enough to worry about it.
    private BufferedReader openFile(File inFile){
        try {
            BufferedReader br = new BufferedReader(new FileReader(inFile));
            return br;
        } catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

    private BufferedReader openFile(String inString){
        try {
            BufferedReader br = new BufferedReader(new FileReader(inString));
            return br;
        } catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

    //Should probably encapsulate the writer stuff too...
}