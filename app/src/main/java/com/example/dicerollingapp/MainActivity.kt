/*
    NAME: ERTUGRUL SAHIN , JOHN OLAYENI , LEVI MAXWELL
    STUDENT NUMBER : A00270022, A00260853 , A00263436
    DESCRIPTION :  We create project dice. We give the side value when creating the dice. After creating the dice, we save these dice sides to the shared pref. We take the data in the shared pref and put it in the local list.
    We show the list on spinner. We ask the spinner to choose to roll the dice.

 */

package com.example.dicerollingapp
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    // when we're get sides to shared Pref. We're adding all side that list
    var dicesList= ArrayList<Int>()
    // storing every random number of side
    var listOfDices=ArrayList<Int>()
    // default side number
    val numberTen:String="10"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Define the views we created in the xml file
        var rollDices:Button=findViewById(R.id.roll_dices)
        var createDice:Button=findViewById(R.id.create_dices)
        val spinner: Spinner = findViewById(R.id.dice_list)
        val sideOfDiceEditText: EditText = findViewById(R.id.side_of_dice)
        val firstDice: TextView = findViewById(R.id.first_dice)
        val secondDice: TextView = findViewById(R.id.second_dice)

        // Defining Shared Pref
        val sharedPref=getPreferences( Context.MODE_PRIVATE)
        // control shared pref has data
        val listOfDice= getStringToSharedPref("listOfDiceSide",sharedPref)
        // if has add to diceList variable
        addToDataToList(listOfDice)
        // diceList variable set spinner
        setDataToSpinner(spinner)

        createDice.setOnClickListener {
            // Since a single number can be added to the edittext, we are checking it
            val isInt=sideOfDiceEditText.text.toString().toIntOrNull()
            // Does the edittext contain commas
            val hasComma=sideOfDiceEditText.text.contains(",")
            // edittext contain the number ten
            val containsTen=sideOfDiceEditText.text.contains(numberTen)

            // if the above conditions are met, the if works
            if((hasComma || isInt!=null) && containsTen){
                // delete any previously recorded data
                sharedPref.edit().clear().commit()

                // add the entered data to the shared pref
                addDataToSharedPref(sharedPref,sideOfDiceEditText)

                // control shared pref has data
                val listOfDice= getStringToSharedPref("listOfDiceSide",sharedPref)

                // if has add to diceList variable
                addToDataToList(listOfDice)

                // diceList variable set spinner
                setDataToSpinner(spinner)

                // all the process are completed, we delete the edittext
                sideOfDiceEditText.text.clear()

            }else{
                // else works if the above conditions are not met
                Toast.makeText(applicationContext,"Undefined input",Toast.LENGTH_LONG).show()
            }


        }

        rollDices.setOnClickListener {
            // check the list we added via shared Pref whether it is empty or not
            if (dicesList.isNotEmpty()){
                // if on spinner is also selected
                if (spinner.selectedItem.toString()==numberTen){
                    // If ten is selected, we are directed to the random method, which gives their multiples
                    listOfDices=rollMultiplesOfTen(spinner.selectedItem.toString().toInt())
                }else{
                    // If ten is not selected, we refer to the method that gives random numbers.
                    listOfDices=roll(spinner.selectedItem.toString().toInt())
                }
                // print the first number generated in random to the textview
                firstDice.text=listOfDices[0].toString()
                // print the second number generated in random to the textview
                secondDice.text=listOfDices[1].toString()
            }else{
                // If the list is empty, we want it to form dice.
                Toast.makeText(applicationContext,"You have to create dice/dices",Toast.LENGTH_LONG).show()
            }


        }
    }
    private fun addDataToSharedPref(sharedPref: SharedPreferences, sideOfDiceEditText: EditText) {
        with (sharedPref.edit()) {
            // here we save individual data with key
            putString("listOfDiceSide", sideOfDiceEditText.text.toString())
            apply()
        }
    }
    private fun setDataToSpinner(spinner: Spinner) {
        var spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,dicesList )
        // Set layout to use when the list of choices appear
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner.adapter=spinnerAdapter
    }
    private fun addToDataToList(listOfDice: String) {
        var listOfDiceSplit=listOfDice.split(",")
        if (listOfDice.isNotEmpty()){
            listOfDiceSplit.forEach {
                dicesList.add(it.toInt())
            }
        }

        dicesList.sort()
    }
    private fun getStringToSharedPref(key: String, sharedPref: SharedPreferences):String{
        return sharedPref.getString(key,"").toString()
    }

    private fun roll(numberOfSides: Int):ArrayList<Int> {
        // Generates random characters from 1 to the number of sides of the dice
        var randomNumbers= ArrayList<Int>()
         val numberOne=Random.nextInt(1, numberOfSides)
         val numberTwo=Random.nextInt(1, numberOfSides)
        randomNumbers.add(numberOne)
        randomNumbers.add(numberTwo)
        return  randomNumbers
    }
    private fun rollMultiplesOfTen(numberOfSides: Int):ArrayList<Int> {
        //  create a random number that gives multiples of ten
        var randomNumbers= ArrayList<Int>()
        val numberOne=Random.nextInt(1, numberOfSides)*numberOfSides
        val numberTwo=Random.nextInt(1, numberOfSides)*numberOfSides
        randomNumbers.add(numberOne)
        randomNumbers.add(numberTwo)
        return  randomNumbers
    }

}