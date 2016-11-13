package com.zizi.project.beginnerandroid3b;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int quantity;
    private String value;
    String priceMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quantity=0;
    }


    //Display Banyaknya Order
    public void display(int number)
    {
        TextView quantityTextView = (TextView)findViewById(R.id.text);
        quantityTextView.setText(""+number);
    }

    //Tambah atau Kurang Order
    public void tambah(View view)
    {
        quantity = quantity + 1;
        display(quantity);
    }

    public void kurang(View view)
    {
        quantity = quantity - 1;
        display(quantity);
    }


    //submit Order yang udah diklik
    public void submitOrder(View view)
    {
        EditText text = (EditText)findViewById(R.id.editNama);
        value = text.getText().toString();

        //Kalo user pilih cheese
        CheckBox cheeseBox = (CheckBox)findViewById(R.id.cheese_id);
        boolean pilihCheese = cheeseBox.isChecked();

        //Kalo user pilih coco milk
        CheckBox cocoMilkBox = (CheckBox)findViewById(R.id.milk_id);
        boolean pilihMilk = cocoMilkBox.isChecked();

        int price  = calculatePrice(quantity, pilihCheese, pilihMilk);
        String order = createOrderSummary (price, pilihCheese, pilihMilk );

        //Intent pesanan ke Email
        Intent keEmail = new Intent(Intent.ACTION_SENDTO);
        keEmail.setData(Uri.parse("mailto:")); //email default
        keEmail.putExtra(Intent.EXTRA_SUBJECT, "Order for " + value);
        keEmail.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if(keEmail.resolveActivity(getPackageManager())!=null)
        {
            startActivity(keEmail);
        }

        displayMassage(order);


    }

    //Order Summary
    public String createOrderSummary(int price, boolean pilihCheese, boolean pilihMilk)
    {
        priceMessage = "Nama Pemesan: "+value;
        priceMessage +="\nPesan Cheese? " +pilihCheese;
        priceMessage +="\nPesan Chocho Milk? " +pilihMilk;
        priceMessage +="\nJumlah yang dipesan: " +quantity;
        priceMessage +="\nTotal Harga: $" +price;
        priceMessage +="\n\nThank you for order :)";
        return priceMessage;
    }


    //Calculate Price Menu was ordered
    private int calculatePrice(int quantity, boolean pilihCheese, boolean pilihMilk)
    {
        int  priceOfCheese=50;
        int priceMilk=20;
        int pricesementara = quantity;

        if(pilihCheese&&pilihMilk)
        {
            pricesementara = (quantity * priceMilk) + (quantity * priceOfCheese);
        }
        else if(pilihMilk)
        {
            pricesementara = quantity * priceMilk;
        }
        else if(pilihCheese)
        {
            pricesementara = quantity * priceOfCheese;
        }
       return pricesementara;
    }

    //display result ordered
    private void displayMassage(String priceMessage)
    {
        TextView result = (TextView)findViewById(R.id.result);
        result.setText(priceMessage);

    }
}
