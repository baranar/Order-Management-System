# Order Management System

### Proje Tanımı
Bu proje, bir **Sipariş Yönetim Sistemi** oluşturmak amacıyla geliştirilmiştir. Masaüstü uygulaması, kullanıcıların sisteme giriş yaparak müşteri, ürün ve sipariş yönetimi işlemlerini kolayca gerçekleştirebilecekleri bir ortam sunar.

---

### Projenin Özellikleri

- **Giriş Paneli:**
  - Sistem kullanıcıları e-posta ve şifre ile giriş yapar.

- **Sekmeler:**
  - **Müşteriler**:
    - Veritabanındaki tüm müşteriler listelenir.
    - Sağ tık ile müşteriler **silinebilir**, **güncellenebilir**.
    - Yeni müşteriler eklenebilir.
    - **Müşteri tipine** ve **isime** göre arama yapılabilir.
  - **Ürünler**:
    - Veritabanındaki tüm ürünler listelenir.
    - Sağ tık ile ürünler **silinebilir**, **güncellenebilir**, **sepete eklenebilir**.
    - Yeni ürünler eklenebilir.
    - **Stok durumuna** ve **isime** göre arama yapılabilir.
  - **Sipariş Oluştur**:
    - İlgili müşteri seçilir.
    - Sepete eklenen ürünler ve ek bilgiler (tarih, not) ile sipariş oluşturulur.
    - Sepet, **temizle** butonu ile sıfırlanabilir.
  - **Siparişler**:
    - Geçmiş siparişlerin tamamı listelenir.

---

### Projede Kullanılan Teknolojiler

- **Java**: Uygulamanın geliştirilmesi.
- **Swing**: Masaüstü grafik arayüzü oluşturma.
- **Veritabanı**: Müşteri, ürün ve sipariş bilgilerini saklama ve yönetme.

---

### Kod Mimarisi

- **Kurumsal Mimari**: Proje, düzenli ve ölçeklenebilir bir yapı için kurumsal mimariye uygun şekilde tasarlandı.
- **Clean Code Prensipleri**: Kodun okunabilirliği ve sürdürülebilirliği için temiz kod yazımı benimsendi.
- **Singleton Design Pattern**: Tekil sınıflar için Singleton tasarım deseni kullanıldı.
- **Özel Veri Tipi**: `ITEM` adlı özel bir veri tipi oluşturularak `ComboBox` gibi bileşenlerde kullanıldı.
- **Metod Bazlı Operasyonlar**: İşlemler küçük metodlara bölünerek düzenli ve sürdürülebilir bir yapı elde edildi.

---

### Uygulamadan Ekran Görüntüleri
<img src="https://github.com/user-attachments/assets/b84c628b-49e3-44fc-8983-b35be6f3a7cb" alt="1_Login ekranı" width="300">
<img src="https://github.com/user-attachments/assets/8cbfd93f-fd29-4d82-8440-dc1356cb49f0" alt="2_MÜşteriler ekranı" width="300">
<img src="https://github.com/user-attachments/assets/39fc4803-6f46-422f-8d3f-3362c4ecfd19" alt="3_Ürünler ekranı" width="300">
<img src="https://github.com/user-attachments/assets/75538f64-fb4c-405e-a4e6-bd5ecd536a50" alt="4_SepeteEklePopup" width="300">
<img src="https://github.com/user-attachments/assets/d809d3c2-6fcf-439f-b8ae-31359905b8fb" alt="5_Sepet" width="300">
<img src="https://github.com/user-attachments/assets/c6e7e5d2-5566-4bdc-a749-e8f202616f9c" alt="6_Sİpariş oluştur" width="300">
<img src="https://github.com/user-attachments/assets/292a7064-3490-4ee2-bf59-f5b9c605ed43" alt="7_Geçmiş Siparişler" width="300">

---

### Projeyi Çalıştırma

1. **Depoyu Klonlayın:**
   ```bash
   git clone https://github.com/baranar/Order-Management-System.git

