/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage.Views.Orders;

import MainPackage.Models.Orders.NewOrderTableModel;
import LibData.Models.Account;
import LibData.Models.Book;
import LibData.Models.OrderLine;
import static LimitedSolution.Utilities.CurrencyHelper.IntToVND;
import static LimitedSolution.Utilities.CurrencyHelper.VNDToInt;
import LimitedSolution.Utilities.JTableHelper;
import MainPackage.Controllers.BookController;
import MainPackage.Controllers.OrderController;
import MainPackage.Models.Book.BookTableModel;
import MainPackage.Models.Orders.NewOrderViewModel;
import MainPackage.Views.Book.BooksFrame;
import MainPackage.Views.Inventory.InventoryFrame;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Limited
 */
public class NewOrderFrame extends javax.swing.JFrame {

    private Account _account;

    private NewOrderFrame _newOrderFrame;

    private OrderController _orderController = new OrderController();
    
    private InventoryFrame _inventoryFrame;
    
    private BooksFrame _booksFrame;
    
//    private AccountFrame

    /**
     * Creates new form NewOrderFrame
     */
    public NewOrderFrame() {
        initComponents();
    }

    public NewOrderFrame(Account _account) {
        this();
        this._account = _account;

        _newOrderFrame = this;
        _newOrderFrame.setVisible(true);

        _newOrderFrame.btnAdvanceSearch.setVisible(false);

        _orderController.ShowBooksTable(_newOrderFrame);

        _newOrderFrame.orderLinesTable.setModel(new NewOrderTableModel());
        JTableHelper.TableColumnAdjuster(_newOrderFrame.orderLinesTable, 40);

        _newOrderFrame.booksTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                LoadSelectedBookInformation();
            }
        });

        _newOrderFrame.orderLinesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                LoadSelectedOrderLineInformation();
            }
        });
    }

    private Book getSelectedBook() {
        Book book = ((BookTableModel) _newOrderFrame.booksTable.getModel()).list.get(
                _newOrderFrame.booksTable.getSelectedRow()
        );

        return book;
    }

    private OrderLine getSelectedOrderLine() {
        OrderLine line = getOrderLineTableModel().list.get(
                _newOrderFrame.orderLinesTable.getSelectedRow()
        );

        return line;
    }

    private NewOrderTableModel getOrderLineTableModel() {
        return ((NewOrderTableModel) _newOrderFrame.orderLinesTable.getModel());
    }

    private void ClearBookInformation() {
        txtIdCode.setText("");
        txtProductName.setText("");
        txtProductPrice.setText("");
        txtProductQuantity.setValue(0);
        txtProductTotalPrice.setText("");
    }

    private void LoadSelectedBookInformation() {
        try {
            Book book = getSelectedBook();

            txtIdCode.setText(book.getIdCode());
            txtProductName.setText(book.getName());
            txtProductPrice.setText(IntToVND(book.getPrice()));
            txtProductQuantity.setValue(0);
            txtProductTotalPrice.setText("");
        } catch (Exception e) {
            ClearBookInformation();
        }
    }

    private void LoadSelectedOrderLineInformation() {
        try {
            OrderLine line = getSelectedOrderLine();

            txOrderLinetIdCode.setText(line.getProductId().getIdCode());
            txtOrderLinePrice.setText(IntToVND(line.getUnitPrice()));
            txtOrderLineTotal.setText(IntToVND(line.getTotalPrice()));
            txtOrderLineQuantity.setValue(line.getQuantity());
        } catch (Exception e) {
            ClearOrderLineInformation();
        }
    }

    private void ClearOrderLineInformation() {
        txOrderLinetIdCode.setText("");
        txtOrderLinePrice.setText("");
        txtOrderLineTotal.setText("");
        txtOrderLineQuantity.setValue(0);
    }

    private void LoadOrderInformation() {
        try {
            Integer totalPrice = getOrderLineTableModel().getTotalPrice();
            txtTotalPrice.setText(IntToVND(totalPrice));

            Integer discountPrice = null;
            try {
                discountPrice = Integer.parseInt(VNDToInt(txtDiscountPrice.getText()) + "");
            } catch (Exception e) {
                txtDiscountPrice.setText("0 VND");
                discountPrice = 0;
            }

            Integer paidPrice = totalPrice - discountPrice;
            txtPaidPrice.setText(IntToVND(paidPrice));
            txtVATPrice.setText(IntToVND(paidPrice / 10));

        } catch (Exception e) {
        }
    }

    private void ClearOrderInformation() {
        ClearBookInformation();
        ClearOrderLineInformation();

        getOrderLineTableModel().list.clear();
        getOrderLineTableModel().fireTableDataChanged();

        txtTotalPrice.setText("");
        txtVATPrice.setText("");
        txtDiscountPrice.setText("");
        txtPaidPrice.setText("");

        txtGuestName.setText("");
        txtGuestAddress.setText("");
        txtGuestEmail.setText("");
        txtGuestPhone.setText("");

        txtDetails.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        btnMakeOrder = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        txtTotalPrice = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtDiscountPrice = new javax.swing.JTextField();
        txtVATPrice = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtPaidPrice = new javax.swing.JTextField();
        txtDiscountWarning = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtGuestName = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtGuestAddress = new javax.swing.JTextField();
        txtGuestPhone = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtGuestEmail = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDetails = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        booksTable = new javax.swing.JTable();
        txtBookSearch = new javax.swing.JTextField();
        btnSimplySearch = new javax.swing.JButton();
        btnAdvanceSearch = new javax.swing.JButton();
        btnReloadBooksList = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        txtProductName = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtIdCode = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtProductPrice = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtProductTotalPrice = new javax.swing.JTextField();
        btnAddOrderLine = new javax.swing.JButton();
        txtProductQuantity = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        orderLinesTable = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txOrderLinetIdCode = new javax.swing.JTextField();
        txtOrderLinePrice = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtOrderLineQuantity = new javax.swing.JSpinner();
        jLabel22 = new javax.swing.JLabel();
        txtOrderLineTotal = new javax.swing.JTextField();
        btnUpdateOrderLine = new javax.swing.JButton();
        btnDeleteOrderLine = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Hóa đơn mới");
        setResizable(false);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chi tiết Hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(255, 0, 0))); // NOI18N

        btnMakeOrder.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnMakeOrder.setText("LẬP HÓA ĐƠN");
        btnMakeOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMakeOrderActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông tin Hóa đơn"));

        txtTotalPrice.setEditable(false);
        txtTotalPrice.setDisabledTextColor(java.awt.Color.black);
        txtTotalPrice.setEnabled(false);
        txtTotalPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalPriceActionPerformed(evt);
            }
        });

        jLabel8.setText("Tổng tiền");

        jLabel9.setText("Khuyến mại");

        txtDiscountPrice.setDisabledTextColor(java.awt.Color.black);
        txtDiscountPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiscountPriceActionPerformed(evt);
            }
        });
        txtDiscountPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDiscountPriceKeyTyped(evt);
            }
        });

        txtVATPrice.setEditable(false);
        txtVATPrice.setDisabledTextColor(java.awt.Color.black);
        txtVATPrice.setEnabled(false);

        jLabel6.setText("Thuế VAT");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Thanh toán");

        txtPaidPrice.setEditable(false);
        txtPaidPrice.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtPaidPrice.setDisabledTextColor(java.awt.Color.black);
        txtPaidPrice.setEnabled(false);

        txtDiscountWarning.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        txtDiscountWarning.setForeground(new java.awt.Color(255, 0, 0));
        txtDiscountWarning.setText("Vui lòng nhập Tiền Khuyến mại đúng định dạng");

        jLabel3.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 0));
        jLabel3.setText("Ấn Enter tại Khuyến mại để cập nhật.");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPaidPrice)
                            .addComponent(txtDiscountPrice)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTotalPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                            .addComponent(txtVATPrice)))
                    .addComponent(txtDiscountWarning, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtVATPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDiscountPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPaidPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(19, 19, 19)
                .addComponent(txtDiscountWarning)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông tin Khách hàng"));

        txtGuestName.setDisabledTextColor(java.awt.Color.black);
        txtGuestName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGuestNameActionPerformed(evt);
            }
        });

        jLabel10.setForeground(new java.awt.Color(255, 0, 0));
        jLabel10.setText("Tên khách");

        jLabel11.setText("Địa chỉ");

        txtGuestAddress.setDisabledTextColor(java.awt.Color.black);

        txtGuestPhone.setDisabledTextColor(java.awt.Color.black);

        jLabel12.setText("Điện thoại");

        jLabel13.setText("Email");

        txtGuestEmail.setDisabledTextColor(java.awt.Color.black);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtGuestName)
                    .addComponent(txtGuestAddress)
                    .addComponent(txtGuestPhone)
                    .addComponent(txtGuestEmail))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGuestName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGuestAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGuestPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGuestEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Ghi chú"));

        txtDetails.setColumns(20);
        txtDetails.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        txtDetails.setLineWrap(true);
        txtDetails.setRows(5);
        jScrollPane3.setViewportView(txtDetails);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMakeOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnMakeOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh mục Sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        booksTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(booksTable);

        txtBookSearch.setPreferredSize(new java.awt.Dimension(6, 23));
        txtBookSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBookSearchActionPerformed(evt);
            }
        });
        txtBookSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBookSearchKeyTyped(evt);
            }
        });

        btnSimplySearch.setText("Tìm kiếm");
        btnSimplySearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimplySearchActionPerformed(evt);
            }
        });

        btnAdvanceSearch.setText("Tìm kiếm nâng cao");
        btnAdvanceSearch.setEnabled(false);

        btnReloadBooksList.setText("Tải lại danh sách");
        btnReloadBooksList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadBooksListActionPerformed(evt);
            }
        });

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thêm Sản phẩm vào Hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        txtProductName.setEditable(false);
        txtProductName.setDisabledTextColor(java.awt.Color.black);
        txtProductName.setEnabled(false);
        txtProductName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProductNameActionPerformed(evt);
            }
        });

        jLabel14.setText("Tên Sản phẩm");

        txtIdCode.setEditable(false);
        txtIdCode.setDisabledTextColor(java.awt.Color.black);
        txtIdCode.setEnabled(false);

        jLabel16.setText("Mã Sản phẩm");

        txtProductPrice.setEditable(false);
        txtProductPrice.setDisabledTextColor(java.awt.Color.black);
        txtProductPrice.setEnabled(false);

        jLabel15.setText("Giá tiền");

        jLabel17.setText("Số lượng");

        jLabel18.setText("Thành tiền");

        txtProductTotalPrice.setEditable(false);
        txtProductTotalPrice.setDisabledTextColor(java.awt.Color.black);
        txtProductTotalPrice.setEnabled(false);

        btnAddOrderLine.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnAddOrderLine.setText("Thêm vào Hóa đơn");
        btnAddOrderLine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddOrderLineActionPerformed(evt);
            }
        });

        txtProductQuantity.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                txtProductQuantityStateChanged(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("Số lượng nằm trong khoảng từ 1 đến 100");

        jLabel2.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setText("Ấn Enter tại Số lượng để cập nhật.");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel15))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtProductPrice, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(txtIdCode, javax.swing.GroupLayout.Alignment.LEADING)))
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE))
                .addGap(48, 48, 48)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(44, 44, 44)
                                .addComponent(txtProductQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(18, 18, 18)
                                .addComponent(txtProductTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnAddOrderLine, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(18, 18, 18)
                        .addComponent(txtProductName)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProductPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel17)
                    .addComponent(txtProductTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(txtProductQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddOrderLine)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txtBookSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSimplySearch)
                        .addGap(18, 18, 18)
                        .addComponent(btnAdvanceSearch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnReloadBooksList))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdvanceSearch)
                    .addComponent(btnReloadBooksList)
                    .addComponent(btnSimplySearch)
                    .addComponent(txtBookSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(255, 0, 0))); // NOI18N

        orderLinesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(orderLinesTable);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Chi tiết dòng hóa đơn"));

        jLabel19.setText("Mã Sản phẩm");

        jLabel20.setText("Giá tiền");

        txOrderLinetIdCode.setEditable(false);
        txOrderLinetIdCode.setDisabledTextColor(java.awt.Color.black);
        txOrderLinetIdCode.setEnabled(false);

        txtOrderLinePrice.setEditable(false);
        txtOrderLinePrice.setDisabledTextColor(java.awt.Color.black);
        txtOrderLinePrice.setEnabled(false);

        jLabel21.setText("Số lượng");

        txtOrderLineQuantity.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                txtOrderLineQuantityStateChanged(evt);
            }
        });

        jLabel22.setText("Thành tiền");

        txtOrderLineTotal.setEditable(false);
        txtOrderLineTotal.setDisabledTextColor(java.awt.Color.black);
        txtOrderLineTotal.setEnabled(false);

        btnUpdateOrderLine.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnUpdateOrderLine.setText("Cập nhật");
        btnUpdateOrderLine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateOrderLineActionPerformed(evt);
            }
        });

        btnDeleteOrderLine.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnDeleteOrderLine.setText("Xóa");
        btnDeleteOrderLine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteOrderLineActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(18, 18, 18)
                        .addComponent(txOrderLinetIdCode))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addGap(46, 46, 46)
                        .addComponent(txtOrderLinePrice))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addGap(31, 31, 31)
                        .addComponent(txtOrderLineTotal))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(40, 40, 40)
                        .addComponent(txtOrderLineQuantity))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(btnDeleteOrderLine, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnUpdateOrderLine, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txOrderLinetIdCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtOrderLinePrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtOrderLineQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(txtOrderLineTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdateOrderLine)
                    .addComponent(btnDeleteOrderLine))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon("C:\\Users\\Limited\\Documents\\GitHub\\MVCJava\\resources\\images\\books.png")); // NOI18N
        jButton1.setToolTipText("Danh mục Sách");
        jButton1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sách", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_BOTTOM, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon("C:\\Users\\Limited\\Documents\\GitHub\\MVCJava\\resources\\images\\inventory.png")); // NOI18N
        jButton2.setToolTipText("Kiểm kê");
        jButton2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Kiểm kê", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_BOTTOM, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon("C:\\Users\\Limited\\Documents\\GitHub\\MVCJava\\resources\\images\\order.png")); // NOI18N
        jButton3.setToolTipText("Hóa đơn");
        jButton3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa đơn", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_BOTTOM, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon("C:\\Users\\Limited\\Documents\\GitHub\\MVCJava\\resources\\images\\Accounts.png")); // NOI18N
        jButton4.setToolTipText("Tài khoản");
        jButton4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tài khoản", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_BOTTOM, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setIcon(new javax.swing.ImageIcon("C:\\Users\\Limited\\Documents\\GitHub\\MVCJava\\resources\\images\\exit.png")); // NOI18N
        jButton5.setToolTipText("Thoát");
        jButton5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thoát", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_BOTTOM, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtTotalPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalPriceActionPerformed

    private void txtGuestNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGuestNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGuestNameActionPerformed

    private void txtBookSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBookSearchActionPerformed
        // TODO add your handling code here:
        _orderController.Find(this, txtBookSearch.getText());
    }//GEN-LAST:event_txtBookSearchActionPerformed

    private void txtBookSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBookSearchKeyTyped
        // TODO add your handling code here:
        //        _bookController.Find(this, txtBookSearch.getText());
    }//GEN-LAST:event_txtBookSearchKeyTyped

    private void btnSimplySearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimplySearchActionPerformed
        // TODO add your handling code here:
        _orderController.Find(this, txtBookSearch.getText());
    }//GEN-LAST:event_btnSimplySearchActionPerformed

    private void btnReloadBooksListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadBooksListActionPerformed
        // TODO add your handling code here:
        txtBookSearch.setText("");
        _orderController.Find(this, txtBookSearch.getText());
    }//GEN-LAST:event_btnReloadBooksListActionPerformed

    private void btnAddOrderLineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddOrderLineActionPerformed
        // TODO add your handling code here:
        _orderController.AddOrderLineToTable(this, getSelectedBook().getProduct(), txtProductQuantity.getValue().toString());
        LoadOrderInformation();
    }//GEN-LAST:event_btnAddOrderLineActionPerformed

    private void txtProductNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProductNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProductNameActionPerformed

    private void txtProductQuantityStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_txtProductQuantityStateChanged
        // TODO add your handling code here:
        try {
            int Quantity = Integer.parseInt(txtProductQuantity.getValue().toString());
            if (!(Quantity >= 1 && Quantity <= 100)) {
                jLabel1.setText("Số lượng đã nhập không hợp lệ.");
                return;
            }

            txtProductTotalPrice.setText(
                    IntToVND(getSelectedBook().getPrice().intValue() * Quantity)
            );
            jLabel1.setText("");
        } catch (Exception e) {
            if (txtProductPrice.getText().isEmpty()) {
                jLabel1.setText("Sản phẩm chưa được thiết lập giá");
            } else {
                jLabel1.setText("Số lượng đã nhập không hợp lệ.");
            }
        }
    }//GEN-LAST:event_txtProductQuantityStateChanged

    private void txtDiscountPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiscountPriceActionPerformed
        // TODO add your handling code here:
        DiscountPriceValueChanged();
    }//GEN-LAST:event_txtDiscountPriceActionPerformed

    private void txtDiscountPriceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscountPriceKeyTyped
        // TODO add your handling code here:
        DiscountPriceValueChanged();
    }//GEN-LAST:event_txtDiscountPriceKeyTyped

    private void txtOrderLineQuantityStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_txtOrderLineQuantityStateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_txtOrderLineQuantityStateChanged

    private void btnUpdateOrderLineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateOrderLineActionPerformed
        // TODO add your handling code here:
        try {
            _orderController.UpdateOrderLineToOrderLinesTable(
                    this,
                    getSelectedOrderLine(),
                    txtOrderLineQuantity.getValue().toString()
            );
            LoadOrderInformation();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn một chi tiết",
                    "Cập nhật hóa đơn",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }//GEN-LAST:event_btnUpdateOrderLineActionPerformed

    private void btnDeleteOrderLineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteOrderLineActionPerformed
        // TODO add your handling code here:
        try {
            _orderController.DeleteOrderLineFromOrderLinesTable(
                    this,
                    getSelectedOrderLine()
            );
            LoadOrderInformation();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn một chi tiết",
                    "Cập nhật hóa đơn",
                    JOptionPane.WARNING_MESSAGE
            );
        }

    }//GEN-LAST:event_btnDeleteOrderLineActionPerformed

    private void btnMakeOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMakeOrderActionPerformed
        // TODO add your handling code here:
        NewOrderViewModel model
                = new NewOrderViewModel(
                        getOrderLineTableModel().list,
                        txtTotalPrice.getText(),
                        txtVATPrice.getText(),
                        txtDiscountPrice.getText(),
                        txtPaidPrice.getText(),
                        txtGuestName.getText(),
                        txtGuestAddress.getText(),
                        txtGuestPhone.getText(),
                        txtGuestEmail.getText(),
                        txtDetails.getText()
                );

        if (_orderController.MakeOrder(this, model, _account)) {
            ClearOrderInformation();
            _orderController.ShowBooksTable(this);
        }
    }//GEN-LAST:event_btnMakeOrderActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if (_inventoryFrame == null)
        {
            _inventoryFrame = new InventoryFrame(_account);
        }
        
        _inventoryFrame.show();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (_booksFrame == null)
        {
            _booksFrame = new BooksFrame(_account);
        }
        
        _booksFrame.show();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void DiscountPriceValueChanged() {
        Integer discountPrice = null;
        try {
            discountPrice = Integer.parseInt(VNDToInt(txtDiscountPrice.getText()) + "");
//            Integer totalPrice = getOrderLineTableModel().getTotalPrice();
//            txtPaidPrice.setText(IntToVND(totalPrice - discountPrice));
            txtDiscountWarning.setText("");

            LoadOrderInformation();
        } catch (Exception e) {
            txtDiscountWarning.setText("Tiền khuyến mại không đúng định dạng.");
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewOrderFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewOrderFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewOrderFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewOrderFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewOrderFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTable booksTable;
    private javax.swing.JButton btnAddOrderLine;
    private javax.swing.JButton btnAdvanceSearch;
    private javax.swing.JButton btnDeleteOrderLine;
    private javax.swing.JButton btnMakeOrder;
    private javax.swing.JButton btnReloadBooksList;
    private javax.swing.JButton btnSimplySearch;
    private javax.swing.JButton btnUpdateOrderLine;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JTable orderLinesTable;
    private static javax.swing.JTextField txOrderLinetIdCode;
    private javax.swing.JTextField txtBookSearch;
    public static javax.swing.JTextArea txtDetails;
    public static javax.swing.JTextField txtDiscountPrice;
    private javax.swing.JLabel txtDiscountWarning;
    public static javax.swing.JTextField txtGuestAddress;
    public static javax.swing.JTextField txtGuestEmail;
    public static javax.swing.JTextField txtGuestName;
    public static javax.swing.JTextField txtGuestPhone;
    private static javax.swing.JTextField txtIdCode;
    private static javax.swing.JTextField txtOrderLinePrice;
    private javax.swing.JSpinner txtOrderLineQuantity;
    private static javax.swing.JTextField txtOrderLineTotal;
    public static javax.swing.JTextField txtPaidPrice;
    private static javax.swing.JTextField txtProductName;
    private static javax.swing.JTextField txtProductPrice;
    private javax.swing.JSpinner txtProductQuantity;
    private static javax.swing.JTextField txtProductTotalPrice;
    public static javax.swing.JTextField txtTotalPrice;
    public static javax.swing.JTextField txtVATPrice;
    // End of variables declaration//GEN-END:variables
}
