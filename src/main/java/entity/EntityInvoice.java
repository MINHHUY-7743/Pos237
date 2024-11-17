
package entity;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class EntityInvoice {
    private int id;
    private Date date;
    private double total;
    private double tax;
    private double grandTotal;

    public EntityInvoice(int id, Date date, double total, double tax, double grandTotal) {
        this.id = id;
        this.date = date;
        this.total = total;
        this.tax = tax;
        this.grandTotal = grandTotal;
    }
    
    // Constructor mặc định
    public EntityInvoice() {
        this.id = 0;
        this.date = new Date();
        this.total = 0.0;
        this.tax = 0.0;
        this.grandTotal = 0.0;
    }
    

//    public Invoice() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }

    // Getters
    public int getId() { return id; }
    public Date getDate() { return date; }
    public double getTotal() { return total; }
    public double getTax() { return tax; }
    public double getGrandTotal() { return grandTotal; }
    
    // Setters
    public void setId(int id) { this.id = id; }
    public void setDate(Date date) { this.date = date; }
    public void setTotal(double total) { 
        this.total = total; 
        this.grandTotal = total + this.tax; // Cập nhật grandTotal khi total thay đổi
    }
    public void setTax(double tax) { 
        this.tax = tax; 
        this.grandTotal = this.total + tax; // Cập nhật grandTotal khi tax thay đổi
    }
    public void setGrandTotal(double grandTotal) { this.grandTotal = grandTotal; }
    
    // Phương thức toString để dễ dàng in thông tin
    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", date=" + date +
                ", total=" + total +
                ", tax=" + tax +
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
               Double.compare(invoice.total, total) == 0 &&
               Double.compare(invoice.tax, tax) == 0 &&
               Double.compare(invoice.grandTotal, grandTotal) == 0 && 
               Objects.equals(date, invoice.date);
    }

    // Phương thức hashCode để hỗ trợ các collection như HashMap
    @Override
    public int hashCode() {
        return Objects.hash(id, date, tax, total, grandTotal);
    }

    // Phương thức để tính tổng tiền (nếu cần)
    public void calculateGrandTotal(List<EntityInvoiceDetail> details) {
        this.grandTotal = details.stream()
            .mapToDouble(detail -> detail.getQuantity() * detail.getPrice())
            .sum();
        this.grandTotal = this.total + this.tax;
    }

    // Phương thức kiểm tra hóa đơn có hợp lệ không
    public boolean isValid() {
        return id > 0 && date != null && total >= 0 && tax >= 0;
    }

    public void setVisible(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    
}
