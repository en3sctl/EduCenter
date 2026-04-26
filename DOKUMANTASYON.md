# EduCenter - Tam Dökümantasyon (MP1 + MP2)

Bu dosya projenin tamamını açıklıyor. Savunmada hoca ne sorarsa sorsun, buraya bakıp hazırlanabilirsin.

---

## İçindekiler

1. [Proje Genel Bakış](#1-proje-genel-bakış)
2. [Dosya Yapısı](#2-dosya-yapısı)
3. [Class Diagram (Görsel)](#3-class-diagram-görsel)
4. [Association Haritası (Görsel)](#4-association-haritası-görsel)
5. [MP1 - Classes & Attributes](#5-mp1---classes--attributes)
6. [MP2 - Associations](#6-mp2---associations)
7. [Her Dosya Ne İşe Yarıyor](#7-her-dosya-ne-işe-yarıyor)
8. [Kritik Kavramlar Sözlüğü](#8-kritik-kavramlar-sözlüğü)
9. [Savunma Soruları ve Cevapları](#9-savunma-soruları-ve-cevapları)

---

## 1. Proje Genel Bakış

**İsim:** EduCenter - Eğitim Merkezi Yönetim Sistemi
**Dil:** Java 17
**Build:** Maven
**Paket:** `mas.educenter`

**Senaryo:** Bir eğitim merkezi var. İçinde öğrenciler (Student) kurslara (Course) kayıt oluyor (Enrollment). Kursları eğitmenler (Instructor) veriyor. Her kurs birden fazla dersten (Lesson) oluşuyor. Kurslar kategorilerde (Category) gruplanıyor.

**MP'lerin birikimli yapısı:**
- MP1: Sınıflar, attribute'lar (temel OOP)
- MP2: İlişkiler (sınıflar birbirine nasıl bağlanıyor)
- MP3: Kalıtım (ileride - Student/Instructor/Employee hiyerarşisi)
- MP4: Veritabanı (ileride - Hibernate ile)

---

## 2. Dosya Yapısı

```
EduCenter/
├── pom.xml                          (Maven config)
├── src/main/java/mas/educenter/
│   ├── ObjectPlus.java              (Extent management base class)
│   ├── Address.java                 (Complex attribute - NOT business class)
│   ├── Person.java                  (Abstract - parent of Student, Instructor)
│   ├── Student.java                 (Öğrenci)
│   ├── Instructor.java              (Eğitmen)
│   ├── Course.java                  (Kurs)
│   ├── Category.java                (Kurs kategorisi)
│   ├── Lesson.java                  (MP2 - Dersin bir parçası, composition)
│   ├── Enrollment.java              (MP2 - Kayıt, association class)
│   └── Main.java                    (Demo - her construct yorumlu burada)
```

**Business class'lar (ObjectPlus extend eder):** Student, Instructor, Course, Category, Lesson, Enrollment, Person
**Yardımcı class'lar:** Address (complex attribute olduğu için ObjectPlus extend ETMEZ)

---

## 3. Class Diagram (Görsel)

```
                    ┌─────────────────────┐
                    │   ObjectPlus        │  (abstract)
                    │  + extent mgmt      │
                    │  + serialization    │
                    └──────────┬──────────┘
                               │
         ┌─────────────────────┼──────────────────┬──────────────┬─────────────┐
         │                     │                  │              │             │
         ▼                     ▼                  ▼              ▼             ▼
  ┌──────────────┐     ┌──────────────┐    ┌──────────┐   ┌──────────┐   ┌──────────────┐
  │Person (abs)  │     │   Course     │    │ Category │   │  Lesson  │   │  Enrollment  │
  │- name        │     │- title       │    │- name    │   │- title   │   │- enrollDate  │
  │- address     │     │- maxCapacity │    │- desc.   │   │- duration│   │- grade       │
  │- birthDate   │     │- lessonDur[] │    │- parent? │   │- order   │   │- status      │
  └──────┬───────┘     │- description?│    │          │   │- course  │   │- student     │
         │             │- instructor  │    │          │   │          │   │- course      │
    ┌────┴────┐        │- category    │    │          │   │          │   │              │
    │         │        │- lessons[]   │    │          │   │          │   │              │
    ▼         ▼        │- enrollments │    │          │   │          │   │              │
┌────────┐ ┌────────┐  └──────────────┘    └──────────┘   └──────────┘   └──────────────┘
│Student │ │Instr.  │
│- stdNo │ │- title │
│- gpa   │ │- exp[] │
│- langs │ │- rate  │
│- advis?│ │- courses│
│- enrol.│ │         │
└────────┘ └────────┘

                              ┌──────────┐
                              │ Address  │  (NOT ObjectPlus)
                              │- street  │  Sadece Serializable
                              │- city    │  Person.address field'ı olarak kullanılıyor
                              │- zipCode │
                              └──────────┘
```

**İşaretler:**
- `?` = optional attribute (null olabilir)
- `[]` = multi-valued (liste)
- `(abs)` = abstract class

---

## 4. Association Haritası (Görsel)

```
                        ╔═══════════════════════════════════════╗
                        ║            ASSOCIATION MAP            ║
                        ╚═══════════════════════════════════════╝

  ┌───────────┐                                      ┌─────────────┐
  │ Instructor│◄═══════════ 1 ◄═══ * ═════════════►│   Course    │
  │           │    binary bidirectional 1-to-*      │             │
  └───────────┘                                      └─────┬───────┘
                                                           │
                                                           │ composition ◆
                                                           │ (course ◆→ lesson)
                                                           ▼
                                                     ┌──────────┐
                                                     │  Lesson  │
                                                     │(whole-part│
                                                     │ olmadan   │
                                                     │ var olmaz)│
                                                     └──────────┘

  ┌───────────┐           ┌────────────┐            ┌─────────────┐
  │  Student  │◄──── 1 ──►│ Enrollment │◄──── 1 ──►│   Course    │
  │           │     *     │(assoc class│     *     │             │
  │           │           │+enrollDate │           │             │
  │           │           │+grade      │           │             │
  │           │           │+status)    │           │             │
  └───────────┘           └────────────┘           └─────────────┘
         ↑                                                   ↑
         │              (Student ↔ Course many-to-many       │
         │               ama EK ATTRIBUTE'LARI VAR)           │
         │                                                    │
         └────────────────────────────────────────────────────┘

  ┌───────────┐                     qualified by title
  │ Category  │◄═════════════════════════════════════►┌─────────┐
  │           │     Map<String, Course>               │ Course  │
  │           │     (hızlı lookup için)               │         │
  └────┬──────┘                                       └─────────┘
       │
       │ recursive bidirectional
       │ (parent ↔ children)
       │
       ▼
  ┌───────────┐
  │ Category  │  (aynı sınıftan, kendine referans)
  │  (child)  │
  └───────────┘
```

---

## 5. MP1 - Classes & Attributes

### 5.1 Construct Listesi (Her biri 2.4 puan = toplam 24)

| # | Construct | Nerede | Örnek |
|---|-----------|--------|-------|
| 1 | Extent + auto-add | ObjectPlus constructor | `new Student(...)` → otomatik extent'e eklenir |
| 2 | Extent persistency | ObjectPlus.writeExtents/readExtents | Serialization ile dosyaya yazılır |
| 3 | Simple attribute | Her sınıfta | `String name`, `int maxCapacity` |
| 4 | Complex attribute | Person.address | `Address` sınıfı (street+city+zip) |
| 5 | Optional attribute | Student.advisor, Course.description | `Optional<String>` dönen getter |
| 6 | Multi-valued attribute | Student.languages | `List<String>` |
| 7 | Class attribute (static) | Student.totalStudents | Tüm student'lar için ortak |
| 8 | Derived attribute | Course.getTotalDuration() | Field yok, hesaplanıyor |
| 9 | Class method (static) | Student.findByLanguage() | Obje yaratmadan çağrılır |
| 10 | Override | toString() her yerde | Üst sınıfı yeniden yazma |
| 10 | Overload | Student.getGpa() ve getGpa(double) | Aynı isim, farklı param |
| - | 2 constructor / class | Her business class | Full + minimal |

### 5.2 ObjectPlus - Sistemin Kalbi

```java
public abstract class ObjectPlus implements Serializable {
    private static Map<Class<? extends ObjectPlus>, List<ObjectPlus>> allExtents = new HashMap<>();

    public ObjectPlus() {
        // Her yeni obje yaratıldığında otomatik extent'e eklenir
        ...
    }
}
```

**Ne yapar?**
- Her business class'ın tüm instance'larını (extent) otomatik takip eder
- `new Student(...)` yazdığında constructor zinciri ile `ObjectPlus()` da çağrılır, obje Student extent'ine eklenir
- `Map<Class, List>` yapısı: her sınıf için ayrı liste
- `writeExtents/readExtents` → serialization ile dosyaya kaydet/yükle

**Neden abstract?** Direkt `new ObjectPlus()` mantıksız - bu bir altyapı. Sadece extend eden sınıflardan obje yaratılmalı.

**Neden `implements Serializable`?** Java serialization yapabilmek için gerekli.

### 5.3 Her Attribute Türü - Neden Nerede

**Simple attribute** → `String name`, `int maxCapacity`
- Tek bir değer tutan basit field

**Complex attribute** → `Address address` (Person içinde)
- Kendi içinde alt alanları olan bir class (street+city+zip)
- Address ayrı bir sınıf ama business class değil (ObjectPlus extend etmez)

**Optional attribute** → `advisor`, `description`, `parentCategory`
- null olabilir
- Getter'lar `Optional<T>` döner ki dışarıya "boş olabilir" mesajı versin

**Multi-valued attribute** → `List<String> languages`, `List<Integer> lessonDurations`
- Birden fazla değer tutar
- Constructor'da defensive copy: `new ArrayList<>(languages)` - dışarıdan gelen listeyi kopyalar ki dışarıdan değiştirilince bizimki etkilenmesin

**Class attribute (static)** → `totalStudents`, `totalCourses`, `averageHourlyRate`
- `static` keyword → sınıfa ait, obje bazında değil
- Tüm instance'lar için ortak
- `Student.totalStudents` diye sınıf üzerinden erişilir

**Derived attribute** → `getFullName()`, `getTotalDuration()`
- Field olarak saklanmaz
- Her çağrıldığında canlı hesaplanır
- `totalDuration` = lessonDurations listesinin toplamı

---

## 6. MP2 - Associations

### 6.1 Construct Listesi (Her biri yaklaşık 6 puan)

| # | Association Türü | EduCenter'da | Nerede |
|---|-----------------|--------------|--------|
| 1 | Binary bidirectional 1-to-* | Instructor ↔ Course | Instructor.courses / Course.instructor |
| 2 | Association with attribute | Student ↔ Enrollment ↔ Course | Enrollment sınıfı |
| 3 | Qualified association | Category → Course by title | Category.coursesByTitle (Map) |
| 4 | Composition | Course ◆→ Lesson | Lesson private constructor + factory |
| + | Recursive bidirectional (bonus) | Category parent ↔ children | Category.subcategories |

### 6.2 Binary Bidirectional 1-to-* (Instructor ↔ Course)

**Nerede:** Bir eğitmen birçok kurs verebilir, bir kursun tek eğitmeni vardır.

**Kod mantığı:**
```java
// Instructor.java
public void addCourse(Course course) {
    if (!courses.contains(course)) {    // FUSE - zaten varsa dur
        courses.add(course);
        course.setInstructor(this);     // ters tarafı da güncelle
    }
}

// Course.java
public void setInstructor(Instructor instructor) {
    if (this.instructor == instructor) return;  // FUSE - aynıysa dur
    if (this.instructor != null) {
        // Eski eğitmenden bu kursu çıkar
        Instructor old = this.instructor;
        this.instructor = null;
        old.removeCourse(this);
    }
    this.instructor = instructor;
    if (instructor != null) {
        instructor.addCourse(this);
    }
}
```

**`contains()` fuse neden?**
- `addCourse` → `setInstructor` → `addCourse` → sonsuz döngü olurdu
- Zaten ekliyse fonksiyon biter, zincir kırılır

### 6.3 Association with Attribute (Student ↔ Course via Enrollment)

**Problem:** Bir öğrenci birçok kursa kayıt olabilir, bir kursta birçok öğrenci olabilir (*-to-*). Ayrıca kayıt tarihi, not, durum gibi **ilişkiye ait** veriler var. Bu veriler ne Student'ın ne Course'un özelliği - kayıt ilişkisinin özelliği.

**Çözüm:** Ayrı bir `Enrollment` sınıfı. Hem Student'a hem Course'a referans tutar + kendi attribute'ları var.

```java
public class Enrollment extends ObjectPlus {
    private Student student;
    private Course course;
    private LocalDate enrollDate;
    private double grade;
    private String status;

    public Enrollment(Student s, Course c, LocalDate date, String status) {
        ...
        student.addEnrollment(this);    // bağlantıyı öğrenciye bildir
        course.addEnrollment(this);     // bağlantıyı kursa da bildir
    }
}
```

**Kullanım:**
```java
new Enrollment(s1, c1, LocalDate.of(2026, 2, 1), "active");
// Artık s1.getEnrollments() ve c1.getEnrollments() bu enrollment'ı görür
```

### 6.4 Composition (Course ◆→ Lesson)

**Kural:** Lesson, Course olmadan var olamaz. Course silinirse Lesson'ları da yok olur. Bir Lesson iki Course'a ait olamaz.

**Kod:**
```java
public class Lesson extends ObjectPlus {
    // PRIVATE constructor - dışarıdan new Lesson yapılamaz
    private Lesson(Course course, String title, int duration, int order) {
        this.course = course;
        ...
    }

    // Tek yaratma yolu bu static method
    public static Lesson createLesson(Course course, String title, int duration, int order) throws Exception {
        if (course == null) {
            throw new Exception("Cannot create a lesson without a course!");
        }
        Lesson lesson = new Lesson(course, title, duration, order);
        course.addLesson(lesson);
        allLessons.add(lesson);  // hangi lesson hangi course'a ait takibi
        return lesson;
    }
}
```

**Private constructor + static factory** = composition kuralları uygulanır

### 6.5 Qualified Association (Category → Course by title)

**Problem:** Normalde bir kategorideki kursu bulmak için listeyi baştan sona taramak gerek (O(n)). Binlerce kurs olsa yavaş olur.

**Çözüm:** Qualifier (anahtar) ile Map kullan. Kurs başlığı qualifier olarak seçilir. `Map<String, Course>` ile O(1) lookup.

```java
public class Category extends ObjectPlus {
    private Map<String, Course> coursesByTitle = new HashMap<>();

    public void addCourse(Course course) {
        String key = course.getTitle();
        if (!coursesByTitle.containsKey(key)) {
            coursesByTitle.put(key, course);
            course.setCategory(this);
        }
    }

    public Course findCourseByTitle(String title) {
        return coursesByTitle.get(title);  // anında bulur
    }
}
```

### 6.6 Recursive Bidirectional (Category parent/children)

**Aynı sınıftan iki obje birbirine bağlı.** Parent bir Category, children de Category.

```java
public void addSubcategory(Category child) {
    if (child == this) return;  // kendisine parent olamaz
    if (!subcategories.contains(child)) {
        subcategories.add(child);
        child.setParentCategory(this);  // ters taraf
    }
}
```

### 6.7 Kritik Pattern: `contains()` Fuse

Her bidirectional association'da aynı pattern kullanılıyor:

```java
public void addX(X item) {
    if (!list.contains(item)) {     // FUSE
        list.add(item);
        item.addY(this);             // ters tarafı çağır
    }
}
```

**Neden çalışıyor?**
1. `a.addX(b)` → b listede yok, ekle, sonra `b.addY(a)` çağrılır
2. `b.addY(a)` → a listede yok, ekle, sonra `a.addX(b)` çağrılır
3. `a.addX(b)` → b **artık listede var**, function biter ← FUSE devreye girdi
4. Zincir kırıldı, sonsuz döngü yok

---

## 7. Her Dosya Ne İşe Yarıyor

### ObjectPlus.java
- Tüm business class'ların base'i
- Constructor'ında obje otomatik extent'e eklenir
- Static methodlar: `writeExtents`, `readExtents`, `getExtent`, `showExtent`

### Address.java
- Complex attribute sınıfı
- **Business class DEĞİL** (ObjectPlus extend etmez)
- Sadece `Serializable` (Person içinde tutulduğu için)
- Fields: street, city, zipCode

### Person.java
- **Abstract** - direkt obje yaratılmaz
- Student ve Instructor'ın ortak parent'ı
- Fields: name, address (complex), birthDate

### Student.java
- Person'dan türer
- **MP1:** studentNo, gpa, languages (multi), advisor (optional), totalStudents (class attr), getFullName (derived), getGpa overload, findByLanguage (class method)
- **MP2:** enrollments list + addEnrollment/removeEnrollment

### Instructor.java
- Person'dan türer
- **MP1:** title, expertise (multi), hourlyRate, averageHourlyRate (class attr), findHighestPaid (class method)
- **MP2:** courses list + addCourse/removeCourse (bidirectional Course ile)

### Course.java
- ObjectPlus'tan türer
- **MP1:** title, maxCapacity, lessonDurations (multi), description (optional), totalCourses (class attr), getTotalDuration (derived), getTotalCourses + findByCapacity (class methods)
- **MP2:** lessons (composition), enrollments, instructor (1-to-*), category (qualified)

### Category.java
- ObjectPlus'tan türer
- **MP1:** name, description, parentCategory (optional self-reference)
- **MP2:** subcategories (recursive), coursesByTitle (qualified Map)

### Lesson.java (YENİ - MP2)
- Course'un composition part'ı
- Private constructor + static `createLesson` factory
- `allLessons` Set ile part sharing engellenir

### Enrollment.java (YENİ - MP2)
- Student ↔ Course association class
- Fields: student, course, enrollDate, grade, status
- Constructor otomatik iki tarafı da bağlar

### Main.java
- **Zorunlu!** Bu olmasa proje değerlendirilmez
- Her construct yorumlu örnek
- MP1 + MP2 demo'ları sıralı

---

## 8. Kritik Kavramlar Sözlüğü

| Kavram | Anlamı |
|--------|--------|
| **Extent** | Bir sınıfın tüm instance'larının listesi |
| **Serialization** | Objeleri binary formata çevirip dosyaya yazmak |
| **Abstract class** | Direkt obje yaratılamayan, sadece extend edilebilen sınıf |
| **Optional\<T\>** | Java wrapper, "bu değer var veya yok" demek için |
| **Static (class)** | Sınıfa ait, objelere değil |
| **Derived attribute** | Saklanmayan, hesaplanan özellik |
| **Overload** | Aynı isimli, farklı parametreli method |
| **Override** | Üst sınıftaki methodu yeniden yazmak |
| **this** | O an üzerinde çalışılan obje |
| **super** | Üst sınıf referansı |
| **Binary association** | İki sınıf arasındaki ilişki |
| **Bidirectional** | Çift yönlü - iki taraf da birbirine erişebilir |
| **Cardinality** | 1-to-1, 1-to-*, *-to-* gibi sayısal ilişki |
| **Composition** | Part whole olmadan var olamaz (güçlü içerme) |
| **Aggregation** | Part whole olmadan da var olabilir (zayıf içerme) |
| **Association class** | İlişkinin kendi attribute'ları için ayrı sınıf |
| **Qualified association** | Anahtar (qualifier) ile hızlı erişim |
| **Recursive association** | Aynı sınıftan objeler arası ilişki |
| **Fuse pattern** | `contains()` kontrolü ile sonsuz döngüyü kırma |
| **Factory method** | Obje yaratma işini bir static methoda devretmek |

---

## 9. Savunma Soruları ve Cevapları

### Genel Sorular

**S: Bu projede kaç sınıfın var?**
C: 10 sınıf. ObjectPlus (altyapı), Address (complex attr), Person (abstract), Student, Instructor, Course, Category, Lesson, Enrollment, Main.

**S: Hangi sınıflar business class?**
C: Student, Instructor, Course, Category, Lesson, Enrollment. Bunlar ObjectPlus'tan türer, extent'leri tutulur. Address business class DEĞİL - sadece Person'ın bir parçası.

### ObjectPlus Soruları

**S: `this.getClass()` ne döndürür?**
C: O an yaratılmakta olan objenin gerçek (concrete) sınıfını. `new Student(...)` çağrısında `this.getClass()` → `Student.class`.

**S: Map\<Class, List\> yerine direkt List tutamaz mıydın?**
C: Tutarsa tek bir liste olur, tüm class'lar karışırdı. Map kullanarak her class'ın extent'i ayrı yerde.

**S: writeExtents sadece map'i yazıyor ama objeler?**
C: Map'in value'ları obje listeleri. Java serialization object graph'ı tamamen serialize eder, yani map'i yazınca içindeki tüm objeler otomatik yazılır.

### Student Soruları

**S: advisor neden Optional dönüyor ama field String?**
C: İçeride null tutmak basit, ama dışarıya Optional dönerek API kullanıcısına "bu boş olabilir" sinyali veriyoruz. Java best practice.

**S: findByLanguage static neden?**
C: Bu işlem belirli bir öğrenciye değil, sınıfın kendisine ait. Tüm extent'i tarıyor, o yüzden bir öğrenci objesine ait olması mantıksız.

**S: getFullName neden derived?**
C: `fullName` diye field yok. getName() + " [student]" formülüyle her çağrıldığında canlı hesaplanır. İsim değişirse otomatik güncel.

### Course Soruları

**S: getTotalDuration her seferinde hesaplanıyor, cache'lesek?**
C: Derived attribute tanımı gereği cache'lenmez. Lesson süreleri değişirse eski cache yanlış olurdu. Hesaplama zaten hızlı (toplama).

**S: totalCourses static neden?**
C: Tüm Course'lar arasında paylaşılan sayaç. Her yeni Course yaratılışında artıyor. Obje bazında değil, sınıf bazında.

### Composition Soruları

**S: Lesson constructor'ı private neden?**
C: Composition kuralı: part whole olmadan var olamaz. Public olsa dışarıdan `new Lesson(null, ...)` yapılabilirdi ve kural çiğnenirdi. Private yapıp createLesson factory method'undan geçmeye zorluyoruz, orada null check var.

**S: allLessons static Set ne işe yarıyor?**
C: Composition'ın "part can't be shared" kuralı. Aynı Lesson iki Course'a ait olamaz. allLessons'ta zaten varsa yeniden eklenemez.

**S: createLesson throws Exception neden?**
C: course null gelirse exception atmalıyız. Bu bir checked exception - çağıran kod ya catch'lemek ya da throws etmek zorunda. Main'de try-catch ile yakalıyoruz.

### Association Soruları

**S: contains() fuse olmasa ne olur?**
C: StackOverflowError. addCourse → setInstructor → addCourse → ... sonsuz özyineleme. Java stack'i dolar, program çöker.

**S: Enrollment yerine direkt Course.students[] tutsak?**
C: enrollDate, grade, status gibi ilişki attribute'larını kaybederdik. Bu veriler öğrenciye de kursa da ait değil - ikisinin arasındaki ilişkiye ait.

**S: Qualified association'ı normal yapsak ne kaybederdik?**
C: Performans. Normal: `for(Course c : courses) if(c.getTitle().equals(x))` → O(n). Qualified: `map.get(x)` → O(1). Büyük veri setinde fark büyük.

**S: Composition ile aggregation farkı?**
C: Aggregation zayıf: part whole'dan bağımsız var olabilir (ör. University - Student). Composition güçlü: part whole olmadan yok olur (ör. Course - Lesson). Course silindiyse Lesson'ın anlamı yok.

### Silme/Güncelleme Soruları

**S: Bir Instructor'dan Course çıkardığında Course.instructor null oluyor mu?**
C: Evet. `removeCourse` metodu `course.setInstructor(null)` çağırıyor, o da karşı tarafı temizliyor. Bidirectional consistency korunuyor.

**S: Bir Course silinirse Lesson'ları ne olur?**
C: `removeCourse()` metodu tüm lessonları `allLessons`'dan disconnect eder ve lessons listesini temizler. Composition kuralı: whole giderse part'lar da gider.

### Genel Java Soruları

**S: LocalDate nedir, Date ile farkı?**
C: Java 8+ java.time paketi. Immutable, thread-safe, sadece tarih (saat yok). Date legacy, problemli.

**S: HashMap vs TreeMap farkı?**
C: HashMap sırasız ama hızlı (O(1)). TreeMap sıralı ama yavaş (O(log n)). Extent için sıra önemli değil, hız önemli.

**S: new ArrayList\<\>(languages) neden, direkt languages yazsak?**
C: Defensive copy. Dışarıdan verilen listeyi kopyalıyoruz. Dışarı verilen liste dışarıda değiştirilirse bizim state'imiz bozulur.

**S: var keyword'ü?**
C: Java 10+ type inference. `var s1 = new Student(...)` → compile-time'da `Student s1 = new Student(...)` anlamına gelir. Sadece local variable'lar için.

---

## Son Söz

Bu dosyayı okumak ve kodu okumak yeterli. Hoca ne sorarsa sor, cevabı burada. Kafana takılan bir şey olursa Main.java'yı çalıştır ve output'u izle, hangi satırın ne ürettiğini gör.

**Savunma taktiği:** Hoca "şu satır ne?" diye sorunca, sakin ol, koda bak, YAPITINI açıkla (neden var, ne iş yapıyor). Hızlı cevap vermeye çalışma - düşünerek ve açıklayarak konuş.
