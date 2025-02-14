package br.com.nelsonssoares.springreview.controllers;

import br.com.nelsonssoares.springreview.exceptions.UnsuportedMathOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/math")
public class MathController {

    private static AtomicLong counter = new AtomicLong();

    @GetMapping("/sum/{numberOne}/{numberTwo}")
    public Double sum(@PathVariable(value = "numberOne") String numberOne,
                      @PathVariable(value = "numberTwo") String numberTwo) {
        if(!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new UnsuportedMathOperation("Please set a numeric value!");

        }
        return convertToDouble(numberOne) + convertToDouble(numberTwo);
    }

    @GetMapping("/minus/{numberOne}/{numberTwo}")
    public Double minus(@PathVariable(value = "numberOne") String numberOne,
                      @PathVariable(value = "numberTwo") String numberTwo) {
        if(!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new UnsuportedMathOperation("Please set a numeric value!");

        }
        return convertToDouble(numberOne) - convertToDouble(numberTwo);
    }

    @GetMapping("/division/{numberOne}/{numberTwo}")
    public Double division(@PathVariable(value = "numberOne") String numberOne,
                      @PathVariable(value = "numberTwo") String numberTwo) {
        if(!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new UnsuportedMathOperation("Please set a numeric value!");

        }
        return convertToDouble(numberOne) / convertToDouble(numberTwo);
    }

    @GetMapping("/avarege/{numberOne}/{numberTwo}")
    public Double avarege(@PathVariable(value = "numberOne") String numberOne,
                           @PathVariable(value = "numberTwo") String numberTwo) {
        if(!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new UnsuportedMathOperation("Please set a numeric value!");

        }
        return (convertToDouble(numberOne) + convertToDouble(numberTwo)) / 2;
    }

    @GetMapping("/squareRoot/{number}")
    public Double squareRoot(@PathVariable(value = "number") String number) {
        if(!isNumeric(number)) {
            throw new UnsuportedMathOperation("Please set a numeric value!");

        }
        return Math.sqrt(convertToDouble(number));
    }


    private Double convertToDouble(String strNumber) {
        if(strNumber == null || strNumber.isEmpty()) {
            throw new UnsuportedMathOperation("Please set a numeric value!");
        }
        String number = strNumber.replace(",", ".");

        return Double.parseDouble(number);
    }

    private boolean isNumeric(String strNumber) {
        if(strNumber == null || strNumber.isEmpty()) {
            return false;
        }
        String number = strNumber.replace(",", ".");
        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }
}
