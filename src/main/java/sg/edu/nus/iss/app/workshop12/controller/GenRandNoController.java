package sg.edu.nus.iss.app.workshop12.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.nus.iss.app.workshop12.exception.RandNoException;
import sg.edu.nus.iss.app.workshop12.models.Generate;

@Controller
@RequestMapping(path="/rand")
public class GenRandNoController {

    // Redirect to the generate.html and show input form
    
    @GetMapping(path="/show")
    public String showRandForm(Model model) {

        // Instantiate the generate object
        // Bind the noOfRandNo to the text field
        Generate g = new Generate(); 
        // Bind the var value to view
        model.addAttribute("generateObj", g); 
        return "generate";
    }

    @PostMapping(path="/generate")
        
        public String postRandNum(@ModelAttribute Generate generate, Model model){
            this.randomizeNum(model, generate.getNumberVal());
            return "result";
        }
    
        private void randomizeNum(Model model, int noOfGenerateNo) {
            int maxGenNo = 30;
            String[] imgNumbers = new String[maxGenNo+1];

            // validate for only accept 0 - 30 
            if(noOfGenerateNo <1 || noOfGenerateNo > maxGenNo) {
                throw new RandNoException();
            }

            //Generate all the number images store into array
            for(int i=0; i<maxGenNo+1; i++) {
                imgNumbers[i] = "number" + i + ".jpg";
            }

            List<String> selectedIMG = new ArrayList<String>();
            Random rnd = new Random();
            Set<Integer> uniqueResult = new LinkedHashSet<Integer>();
            while(uniqueResult.size()<noOfGenerateNo){
                Integer resultOfRand = rnd.nextInt(noOfGenerateNo);
                uniqueResult.add(resultOfRand);

            }
            
            Iterator<Integer> it = uniqueResult.iterator();
            Integer currElem = null;
            while(it.hasNext()){
                currElem = it.next();
                selectedIMG.add(imgNumbers[currElem.intValue()]);
            }

            model.addAttribute("numberRandomNum", noOfGenerateNo);
            model.addAttribute("randNumResult", selectedIMG.toArray());
        }
}
