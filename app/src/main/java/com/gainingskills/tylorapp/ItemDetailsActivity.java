package com.gainingskills.tylorapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import soup.neumorphism.NeumorphButton;

public class ItemDetailsActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    TextView name,price;
    ImageView imageView;
    NeumorphButton orderBtn;
    String userName,phoneNumberUser,userImage;
    Context context;
    private String button ="me";
    DatabaseReference rootRef;

    String cName,cPrice,cImage,itemId,uid;
    String pushId;
    String pushNewId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        imageView = findViewById(R.id.adsDetailPic);
        orderBtn = findViewById(R.id.ItemOrderBtnId);
        name = findViewById(R.id.ItemDetailName);
        price = findViewById(R.id.ItemDetailPrice);
        mToolbar = findViewById(R.id.toolbarIDItem);
        mToolbar.setTitle("Item Details");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        context = ItemDetailsActivity.this;
         cName = getIntent().getExtras().getString("name");
         cPrice = getIntent().getExtras().getString("price");
         cImage = getIntent().getExtras().getString("image");
         itemId = getIntent().getExtras().getString("itemId");
        name.setText(cName);
        price.setText(cPrice);
         uid = FirebaseAuth.getInstance().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User");
        ref.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    userName = snapshot.child("name").getValue().toString();
                    phoneNumberUser = snapshot.child("phone").getValue().toString();
                    userImage = snapshot.child("image").getValue().toString();




                } else {

                    Toast.makeText(ItemDetailsActivity.this, "User Name couldn't found", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        rootRef = FirebaseDatabase.getInstance().getReference("PushIds");

        rootRef.child(itemId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    try {
                        pushNewId = snapshot.child("push").getValue().toString();
                        DatabaseReference rootNode = FirebaseDatabase.getInstance().getReference("Orders");
                        rootNode.child(uid).child(pushNewId).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {

                                    button = snapshot.child("button").getValue().toString();
                                    if (button.equals("Pending")){
                                        orderBtn.setText(button);

                                    }
                                    else {

                                    }


                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    catch (Exception e){}


                }else {}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        Glide.with(ItemDetailsActivity.this).load(cImage).placeholder(R.drawable.categories).into(imageView);


        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (button.equals("Pending")) {

                    showCustomDialog(1);



                }
                else {
                    showCustomDialog(0);
                }


            }
        });




    }



    public void showCustomDialog(int i) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.order_confirmation_dailog, null, false);

        NeumorphButton submitBtn,okayBtnCancel,cancelBtn,orderCancelBtn;
        TextView sureAsk = view.findViewById(R.id.sureTvId);
        submitBtn = view.findViewById(R.id.submitBtnId);
        cancelBtn = view.findViewById(R.id.cancelBtnId);
        okayBtnCancel = view.findViewById(R.id.dismissBtnId);
        TextView pendingTv = view.findViewById(R.id.pendingTv);
        RelativeLayout ll = view.findViewById(R.id.ll1earning);
        orderCancelBtn = view.findViewById(R.id.yesBtnId);

        if (i==1){
            pendingTv.setVisibility(View.VISIBLE);
            okayBtnCancel.setVisibility(View.VISIBLE);
            orderCancelBtn.setVisibility(View.VISIBLE);
            cancelBtn.setVisibility(View.GONE);
            sureAsk.setVisibility(View.GONE);
            ll.setVisibility(View.GONE);


        }
        okayBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }

        });


        orderCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderCancelBtn.setClickable(false);
                orderCancelBtn.setText("Wait");

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Orders");

                reference.child(uid).child(pushNewId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ItemDetailsActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        orderBtn.setText("Order");
                        onBackPressed();
                    }
                });

            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                rootRef = FirebaseDatabase.getInstance().getReference("Orders");
                pushId = rootRef.push().getKey();
                HashMap<String, Object> map = new HashMap<>();
                map.put("itemId",itemId);
                map.put("pushId",pushId);


                rootRef.child(uid).child(pushId).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){


                            DatabaseReference root = FirebaseDatabase.getInstance().getReference("Orders").child(uid).child(pushId);
                            HashMap<String, Object> map1 = new HashMap<>();
                            map1.put("itemprice",cPrice);
                            map1.put("itemname",cName);
                            map1.put("itemimage",cImage);
                            map1.put("itemId",itemId);
                            map1.put("button","Pending");
                            map1.put("pushId",pushId);
                            map1.put("uid",uid);

                            root.setValue(map1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(ItemDetailsActivity.this, "Order placed successfully", Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                    orderBtn.setText("Pending");

                                    DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("User").child(uid);
                                    databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()){
                                                String customerName = snapshot.child("name").getValue().toString();
                                                String customerImage = snapshot.child("image").getValue().toString();

                                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("UserOrders");

                                                HashMap<String, Object> map2 = new HashMap<>();
                                                map2.put("userId",uid);
                                                map2.put("CustomerImage",customerImage);
                                                map2.put("CustomerName",customerName);
                                                databaseReference.child(uid).setValue(map2).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        DatabaseReference db =FirebaseDatabase.getInstance().getReference("PushIds");
                                                        HashMap<String, Object> map5 = new HashMap<>();
                                                        map5.put("userId",uid);
                                                        map5.put("push",pushId);
                                                        map.put("itemId",itemId);
                                                        db.child(itemId).setValue(map5).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                               onBackPressed();
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                            else {}

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });





                                }
                            });



                        }
                        else {
                            Toast.makeText(ItemDetailsActivity.this, task.getResult().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ItemDetailsActivity.this, "Error: "+e, Toast.LENGTH_SHORT).show();
                    }
                });







            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.setContentView(view);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
       // window.setBackgroundDrawableResource(R.color.Transparent);
        window.setGravity(Gravity.CENTER);
        dialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.whatsbtn,menu);

        MenuItem item = menu.findItem(R.id.whatsBtnId);

        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (item.getItemId()){

                    case R.id.whatsBtnId:

                        try {

                            String text = "Asalam o Alikom,\n";

                            String toNumber = "+923481967805";

                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + toNumber + "&text=" + text));
                            startActivity(intent);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;

                }
                return true;
            }
        });



        return super.onCreateOptionsMenu(menu);
    }


}