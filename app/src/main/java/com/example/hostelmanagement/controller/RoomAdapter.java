package com.example.hostelmanagement.controller;

import androidx.appcompat.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color; // Import Color class
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostelmanagement.DAO.ContractDAO; // Import ContractDAO
import com.example.hostelmanagement.DAO.RoomDAO;
import com.example.hostelmanagement.R;
import com.example.hostelmanagement.model.Contract; // Import Contract model
import com.example.hostelmanagement.model.Room;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {
    private Context context;
    private List<Room> roomList;
    private ContractDAO contractDAO; // Declare ContractDAO

    public RoomAdapter(Context context, List<Room> roomList) {
        this.context = context;
        this.roomList = roomList;
        this.contractDAO = new ContractDAO(context); // Initialize ContractDAO
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_room, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = roomList.get(position);
        holder.tvRoomName.setText(room.getRoomName());
        holder.tvPrice.setText("Giá: " + room.getPrice() + " đ/tháng");

        if (room.isStatus()) {
            holder.tvStatus.setText("● Đang thuê");
            holder.tvStatus.setTextColor(Color.parseColor("#4CAF50"));
            holder.btnCreateContract.setText("Xem hợp đồng");
            holder.btnCreateContract.setBackgroundColor(Color.parseColor("#2196F3"));

            // Ẩn nút sửa và xóa
            holder.btnEdit.setVisibility(View.GONE);
            holder.btnDelete.setVisibility(View.GONE);

            // Hiển thị hợp đồng
            Contract contract = contractDAO.getContractByRoomNumber(room.getId());
            if (contract != null) {
                holder.tvCustomerName.setText("Người thuê: " + contract.getCustomerName());
                holder.tvCustomerPhone.setText("SĐT: " + contract.getPhone());
                holder.tvCustomerName.setVisibility(View.VISIBLE);
                holder.tvCustomerPhone.setVisibility(View.VISIBLE);
            } else {
                holder.tvCustomerName.setVisibility(View.GONE);
                holder.tvCustomerPhone.setVisibility(View.GONE);
                Log.e("RoomAdapter", "No contract found for rented room: " + room.getRoomName());
            }

            holder.btnCreateContract.setOnClickListener(v -> {
                if (contract != null) {
                    Intent intent = new Intent(context, ViewContract.class);
                    intent.putExtra("contract_id", contract.getId());
                    context.startActivity(intent);
                }
            });

        } else {
            holder.tvStatus.setText("● Đang trống");
            holder.tvStatus.setTextColor(Color.parseColor("#FF3B30"));
            holder.btnCreateContract.setText("Tạo hợp đồng");
            holder.btnCreateContract.setBackgroundColor(Color.parseColor("#FF5722"));

            holder.btnEdit.setVisibility(View.VISIBLE);
            holder.btnDelete.setVisibility(View.VISIBLE);

            holder.tvCustomerName.setVisibility(View.GONE);
            holder.tvCustomerPhone.setVisibility(View.GONE);

            holder.btnCreateContract.setOnClickListener(v -> {
                Intent intent = new Intent(context, CreateContract.class);
                intent.putExtra("room_id", room.getId());
                intent.putExtra("room_name", room.getRoomName());
                context.startActivity(intent);
            });
            holder.btnEdit.setOnClickListener(v -> {
                Intent intent = new Intent(context, EditRoom.class);
                intent.putExtra("room_id", room.getId());
                context.startActivity(intent);
            });

            holder.btnDelete.setOnClickListener(v -> {
                new AlertDialog.Builder(context)
                        .setTitle("Xác nhận xoá")
                        .setMessage("Bạn có chắc chắn muốn xoá phòng này?")
                        .setPositiveButton("Xoá", (dialog, which) -> {
                            int deletedRows = new RoomDAO(context).deleteRoom(room.getId());
                            if (deletedRows > 0) {
                                roomList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, roomList.size());
                            }
                        })
                        .setNegativeButton("Huỷ", null)
                        .show();
            });


        }
    }


    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView tvRoomName, tvPrice, tvStatus, tvCustomerName, tvCustomerPhone; // Add new TextViews
        ImageView btnEdit, btnDelete;
        Button btnCreateContract;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoomName = itemView.findViewById(R.id.tvRoomName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvCustomerName = itemView.findViewById(R.id.tvCustomerName); // Initialize new TextViews
            tvCustomerPhone = itemView.findViewById(R.id.tvCustomerPhone); // Initialize new TextViews
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnCreateContract = itemView.findViewById(R.id.btnCreateContract);
        }
    }
}