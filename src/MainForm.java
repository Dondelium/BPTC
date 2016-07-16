import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;

/**
 * Created by Dondelium on 7/13/2016.
 */
public class MainForm {
    private JTextField bpName;
    public JButton selectBP;
    private JTextArea bpCompsOutput;
    private JButton exitButton;
    private JPanel mainPanel;
    private JFrame parent;
    private BlockDefinitions blockDefs;

    public MainForm() {
        parent = (JFrame)SwingUtilities.getWindowAncestor(mainPanel);
        blockDefs = new BlockDefinitions();

        selectBP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FileDialog fd = new FileDialog(parent, "test", FileDialog.LOAD);
                fd.setVisible(true);
                File out = fd.getFiles()[0];
                String path = fd.getDirectory() + fd.getFile();
                bpName.setText(path);
                bpCompsOutput.setText("");
                bpCompsOutput.append("Processing file: "+path+"\n");
                if(path != null){
                    try {
                        processFile(out);
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int reply = JOptionPane.showConfirmDialog(null, "Are you sure you wish to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }


    private void processFile(File in) throws IOException{
        ArrayList<String> blockNames = new ArrayList<String>();
        String line = "";

        BufferedReader br = new BufferedReader(new FileReader(in));
        while ((line = br.readLine()) != null) {
            if (line.contains("<SubtypeName>")) {
                int index1 = line.indexOf(">");
                int index2 = line.indexOf("<", index1);
                line = line.substring(index1+1,index2);
                blockNames.add(line);
            }
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
                        componentNums.set(index, (Integer)firstVal);
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
}


