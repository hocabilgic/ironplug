# IronPlug Projesi

Bu projeyi Docker kullanarak çalıştırmak ve Render.com'a deploy etmek için gerekli adımlar.

## Docker ile Çalıştırma

### Gereksinimler
- Docker
- Docker Compose

### Adımlar

1. Projeyi klonlayın:
   ```
   git clone <repo-url>
   cd ironpluga
   ```

2. Docker Compose ile çalıştırın:
   ```
   docker-compose up -d
   ```

3. Uygulama http://localhost:8082 adresinde çalışacaktır
   - Swagger UI: http://localhost:8082/swagger-ui.html

4. Uygulamayı durdurmak için:
   ```
   docker-compose down
   ```

## Render.com'da Deploy Etme

1. Render.com hesabınızda yeni bir web servis oluşturun.
2. GitHub repo bağlantısını kurun.
3. Aşağıdaki ortam değişkenlerini ayarlayın:
   - `SPRING_PROFILES_ACTIVE`: docker
   - `MAIL_USERNAME`: Mail hesap adresiniz
   - `MAIL_PASSWORD`: Mail şifreniz
   - `POSTGRES_USER`: Veritabanı kullanıcı adı
   - `POSTGRES_PASSWORD`: Veritabanı şifresi

4. Dağıtım ayarları:
   - Ortam: Docker
   - Dockerfile Yolu: ./Dockerfile
   - Docker Bağlamı: .

5. "Create Web Service" düğmesine tıklayın.

## Önemli Notlar

1. **Hassas Bilgileri Korumanız Gerekir**: 
   - Mail şifresi ve veritabanı kimlik bilgileri gibi hassas bilgileri doğrudan kodunuza eklememelisiniz.
   - Render.com'da ortam değişkenleri olarak ayarlayın.

2. **Veritabanı Yedekleri**:
   - Render.com üzerindeki PostgreSQL veritabanınızı düzenli olarak yedekleyin.

3. **SSL/HTTPS**:
   - Render.com varsayılan olarak SSL sağlar, ek ayar gerekmez.