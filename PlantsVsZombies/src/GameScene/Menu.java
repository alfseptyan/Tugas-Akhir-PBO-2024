package GameScene;

import Main.GameWindow;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import javax.swing.*;

public class Menu extends JPanel {
    Image bgImage; // Variable untuk menyimpan gambar latar belakang
    private JButton playButton; // Tombol untuk memulai permainan
    private JButton creditButton; //Tombol untuk credit
    private JButton exitButton; // Tombol untuk keluar dari aplikasi

    public Menu() {
        initComponents(); // Metode untuk menginisialisasi komponen
        setSize(1012, 785); // Mengatur ukuran panel
        // Memuat gambar latar belakang dari direktori resources
        bgImage = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/images/main menu...jpg"))).getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Menggambar gambar latar belakang pada panel
        g.drawImage(bgImage, 0, 0, null);
    }

    private void initComponents() {
        int x = 200;
        int y = 100;
        int width = 1012;
        int height = 785;
        setPreferredSize(new Dimension(width, height)); // Mengatur ukuran preferensi panel
        setLayout(null); // Mengatur layout manager menjadi null untuk penempatan bebas komponen

        // Inisialisasi tombol play
        playButton = new JButton();
        playButton.setBounds((width - x) / 2, (height - y) / 2-30, 200, 50); // Mengatur posisi dan ukuran tombol
        playButton.setBackground(Color.WHITE); // Mengatur warna latar belakang tombol
       playButton.setBorderPainted(false); // Menghilangkan border tombol
        playButton.setContentAreaFilled(false); // Membuat tombol transparan
        playButton.setFocusPainted(false); // Menghilangkan efek fokus pada tombol
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameWindow.begin(); // Memulai permainan ketika tombol diklik
            }
        });
        add(playButton); // Menambahkan tombol ke panel

        // Inisiasi tombol credit
        creditButton = new JButton();
        creditButton.setBounds((width - x) / 2, (height - y) / 2 + 60, 200, 50);
        creditButton.setBackground(Color.WHITE);
        creditButton.setBorderPainted(false);
        creditButton.setContentAreaFilled(false);
        creditButton.setFocusPainted(false);
        creditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Image image = Toolkit.getDefaultToolkit().getImage("PlantsVsZombies/src/images/creditnew.png");
                Image resizedImage = image.getScaledInstance(1012, 685, Image.SCALE_SMOOTH); // set desired width and height
                ImageIcon imageIcon = new ImageIcon(resizedImage);
                JLabel imageLabel = new JLabel(imageIcon);
            JOptionPane.showMessageDialog(null, imageLabel, "Credit", JOptionPane.PLAIN_MESSAGE);
            }
        });
        add(creditButton);



        // Inisialisasi tombol exit
        exitButton = new JButton();
        exitButton.setBounds((width - x) / 2, (height - y) / 2 + 150, 200, 50); // Mengatur posisi dan ukuran tombol
        exitButton.setBackground(Color.WHITE); // Mengatur warna latar belakang tombol
        exitButton.setBorderPainted(false); // Menghilangkan border tombol
        exitButton.setContentAreaFilled(false); // Membuat tombol transparan
        exitButton.setFocusPainted(false); // Menghilangkan efek fokus pada tombol
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Menutup aplikasi ketika tombol diklik
            }
        });
        add(exitButton); // Menambahkan tombol ke panel
    }
}