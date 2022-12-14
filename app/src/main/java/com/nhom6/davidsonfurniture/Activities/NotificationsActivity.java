package com.nhom6.davidsonfurniture.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;

import com.nhom6.davidsonfurniture.Adapters.NotificationAdapter;
import com.nhom6.davidsonfurniture.Models.Notification;
import com.nhom6.davidsonfurniture.R;
import com.nhom6.davidsonfurniture.databinding.ActivityNotificationsBinding;

import java.util.ArrayList;

public class NotificationsActivity extends AppCompatActivity {

    ActivityNotificationsBinding binding;
    NotificationAdapter adapter;
    ArrayList<Notification> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_notifications);

        //hide status and action bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        binding = ActivityNotificationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadData();
        toNotificationDetails();
        toHome();

        deleteItem();

        readAll();
    }

    private void readAll() {
        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(NotificationsActivity.this);
                builder.setTitle("X??c nh???n x??a!");
                builder.setMessage("B???n ch???c ch???n x??a t???t c??? th??ng b??o?");
                builder.setIcon(R.mipmap.ic_launcher_round);
                builder.setPositiveButton("?????ng ??", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notificationList.clear();
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("H???y", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setCancelable(false);
                builder.create().show();
            }
        });
    }

    private void deleteItem() {
        //delete an item
        binding.lvNotifications.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder= new AlertDialog.Builder(NotificationsActivity.this);
                builder.setTitle("X??c nh???n x??a!");
                builder.setMessage("B???n ch???c ch???n x??a th??ng b??o n??y?");
                builder.setIcon(R.mipmap.ic_launcher_round);
                builder.setPositiveButton("?????ng ??", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notificationList.remove(i);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("H???y", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setCancelable(false);
                builder.create().show();

                return true;
            }
        });
    }

    private void loadData() {
        notificationList = new ArrayList<>();
        notificationList.add(new Notification(R.drawable.ic_notifications_cancel_order,
                "????n h??ng ???? hu???", "Nh???n ????? xem chi ti???t"));
        notificationList.add(new Notification(R.drawable.ic_notification_delivered,
                "Giao h??ng th??nh c??ng", "Nh???n ????? xem chi ti???t"));
        notificationList.add(new Notification(R.drawable.ic_status_delivering_inactive,
                "????n h??ng ??ang ???????c v???n chuy???n", "Nh???n ????? xem chi ti???t"));
        notificationList.add(new Notification(R.drawable.ic_pick_voucher,
                "B???n c?? m?? gi???m gi?? g???n h???t h???n", "Nh???n ????? xem chi ti???t"));

        adapter = new NotificationAdapter(NotificationsActivity.this, R.layout.item_notification, notificationList);
        binding.lvNotifications.setAdapter(adapter);
    }

    private void toNotificationDetails() {
        binding.lvNotifications.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(NotificationsActivity.this, NotificationDetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void toHome() {
        binding.toolbarNotifications.getChildAt(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}