
package entity;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class EntityInvoice {
    private int id;
    private Date date;
    private double grandTotal;

    public EntityInvoice(int id, Date date, double grandTotal) {
        this.id = id;
        this.date = date;
        this.grandTotal = grandTotal;
    }
    
    // Constructor mặc định
    public EntityInvoice() {
        this.id = 0;
        this.date = new Date();
        this.grandTotal = 0.0;
    }
    

//    public Invoice() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }

    // Getters
    public int getId() { return id; }
    public Date getDate() { return date; }
    public double getGrandTotal() { return grandTotal; }
    
    // Setters
    public void setId(int id) { this.id = id; }
    public void setDate(Date date) { this.date = date; }
    public void setGrandTotal(double grandTotal) { this.grandTotal = grandTotal; }
    
    // Phương thức toString để dễ dàng in thông tin
    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", date=" + date +
                ", grandTotal=" + grandTotal +
                '}';
    }
    
    // Phương thức equals để so sánh các đối tượng Invoice
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityInvoice invoice = (EntityInvoice) o;
        return id == invoice.id && 
               Double.compare(invoice.grandTotal, grandTotal) == 0 && 
               Objects.equals(date, invoice.date);
    }

    // Phương thức hashCode để hỗ trợ các collection như HashMap
    @Override
    public int hashCode() {
        return Objects.hash(id, date, grandTotal);
    }

    // Phương thức để tính tổng tiền (nếu cần)
    public void calculateGrandTotal(List<EntityInvoiceDetail> details) {
        this.grandTotal = details.stream()
            .mapToDouble(detail -> detail.getQuantity() * detail.getPrice())
            .sum();
    }

    // Phương thức kiểm tra hóa đơn có hợp lệ không
    public boolean isValid() {
        return id > 0 && date != null && grandTotal >= 0;
    }

    public void setVisible(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    
}
