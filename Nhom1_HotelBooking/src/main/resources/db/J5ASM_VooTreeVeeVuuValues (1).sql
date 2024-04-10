--use J5ASM_VooTreeVeeVuu

-- create trigger trg_createwallet_from_User on users
-- after insert
-- as
-- begin
-- 	insert into wallet values(0,getdate(),(select email from inserted))
-- end;


insert into users values('dungnhps26555@fpt.edu.vn','1999-09-02',N'Dũng','Nam',N'Nguyễn Hoàng','0773881029','Active')
insert into users values('thuannnmps26548@fpt.edu.vn','2000-03-12',N'Thuận','Nam',N'Nguyễn Ngọc Minh','0909990099','Active')
insert into users values('giauntnps26628@fpt.edu.vn','2002-11-13',N'Giàu',N'Nữ',N'Nguyễn Thụy Ngọc','0770023321','Active')
insert into users values('huydgps26598@fpt.edu.vn','2002-07-23',N'Huy','Nam',N'Đỗ Gia','0900002232','Active')


INSERT INTO [dbo].[accounts]
    ([password], [role], [email])
VALUES
    ('123', 'ADMIN', 'dungnhps26555@fpt.edu.vn'),
    ('123', 'ADMIN', 'thuannnmps26548@fpt.edu.vn'),
    ('123', 'ADMIN', 'giauntnps26628@fpt.edu.vn'),
    ('123', 'ADMIN', 'huydgps26598@fpt.edu.vn')
GO

insert into room_type values('STANDARD',2,N'1 Giường cỡ King','standard_king.jpg',300000,'Standard - Couple Room'),
('STANDARD2',4,N'2 Giường cỡ King','standard_2king.jpg',450000,'Standard - Family Room'),
('DELUXE',2,N'1 Giường cỡ King','deluxe_king.jpg',400000,'Deluxe - Couple Room'),
('DELUXE2',4,N'2 Giường cỡ King','deluxe_2king.jpg',700000,'Deluxe - Double Room'),
('DELUXE4',6,N'2 Giường cỡ King - 1 Giường cỡ vừa','deluxe_2king1small.jpg',950000,'Deluxe - Family Room'),
('SINGLE',1,N'1 Giường thường','single.jpg',100000,'Single Room'),
('DOUBLE',2,N'1 Giường cỡ King','double_king.jpg',170000,'Double - Couple Room'),
('TWIN',2,N'2 Giường thường','twin.jpg',200000,'Twin Room'),
('TRIPLE',3,N'1 Giường cỡ King - 1 Giường thường','triple.jpg',350000,'Triple Room'),
('FAMILY',5,N'2 Giường cỡ King','quad.jpg',500000,'Family Room')


INSERT INTO [dbo].[hotels]
    ([hotelid], [address], [city], [description], [email], [hotelname], [image], [phone_num], [stars], [status])
VALUES
    ('H001', N'159 Thùy Vân, Thắng Tam', N'Vũng Tàu', N'The Imperial Vung Tau Hotel & Resort là lựa chọn sáng giá dành cho những ai đang tìm kiếm một trải nghiệm xa hoa đầy thú vị trong kỳ nghỉ của mình.', 'imperial@gmail.com', N'The Imperial Vung Tau', 'H001.jpg' , '0254362888', 5, 'Active'),
    ('H002', N'80 Triệu Việt Vương, phường 3', N'Đà Lạt', N'La Paix Dalat là đề xuất hàng đầu dành cho những tín đồ du lịch "bụi" mong muốn được nghỉ tại một khách sạn vừa thoải mái lại hợp túi tiền.', 'lapaix@gmail.com', N'La Paix Dalat', 'H002.jpg' , '0908812900', 3, 'Active'),
    ('H003', N'11 Trần Thị Lý, Quận Hải Châu', N'Đà Nẵng', N'Dành cho những du khách muốn du lịch thoải mái cùng ngân sách tiết kiệm, Gold Plaza Hotel Da Nang sẽ là lựa chọn lưu trú hoàn hảo, nơi cung cấp các tiện nghi chất lượng và dịch vụ tuyệt vời.', 'goldplaza@gmail.com', N'Gold Plaza Hotel Da Nang', 'H003.jpg' , '0236387455', 4, 'Active'),
    ('H004', N'Bãi Khem, Phường An Thới, An Thới', N'Phú Quốc', N'Hãy tận hưởng thời gian vui vẻ cùng cả gia đình với hàng loạt tiện nghi giải trí tại New World Phu Quoc Resort, một nơi nghỉ tuyệt vời phù hợp cho mọi kỳ nghỉ bên người thân.', 'newworldpq@gmail.com', N'New World Phu Quoc Resort', 'H004.jpg' , '0297371666', 5, 'Active'),
    ('H005', N'Khu 1, Cồn Cái Khế, Phường Cái Khế, Cái Khế, Ninh Kiều', N'Cần Thơ', N'Dành cho những du khách muốn du lịch thoải mái cùng ngân sách tiết kiệm, Muong Thanh Luxury Can Tho Hotel sẽ là lựa chọn lưu trú hoàn hảo, nơi cung cấp các tiện nghi chất lượng và dịch vụ tuyệt vời.', 'muongthanhct@gmail.com', N'Muong Thanh Luxury Can Tho Hotel', 'H005.jpg' , '090881290', 4, 'Active'),
    ('H006', N'Đảo Hòn Tre, Vĩnh Nguyên', N'Nha Trang', N'Khách sạn này là nơi tốt nhất dành cho những ai mong muốn một nơi thanh bình, thư thái để ẩn mình khỏi đám đông ồn ã, xô bồ.', 'vinpearlnt@gmail.com', N'Vinpearl Resort Nha Trang', 'H006.jpg' , '0258359822', 5, 'Active'),
    ('H007', N'Huynh Thuc Khang, Phường Mũi Né', N'Phan Thiết', N'Centara Mirage Resort Mũi Né là một khu nghỉ dưỡng kết hợp giải trí, hứa hẹn là một nơi mà bạn có thể tận hưởng được nhiều thứ.', 'centara@gmail.com', N'Centara Mirage Resort Mui Ne', 'H007.jpg' , '0252222220', 4, 'Active'),
    ('H008', N'12 Hạ Long, phường Bãi Cháy', N'Hạ Long', N'Khách sạn này là lựa chọn hoàn hảo cho các kỳ nghỉ mát lãng mạn hay tuần trăng mật của các cặp đôi. Quý khách hãy tận hưởng những đêm đáng nhớ nhất cùng người thương của mình tại Wyndham Legend Halong Hotel', 'wyndham@gmail.com', N'Wyndham Legend Halong Hotel', 'H008.jpg' , '0203363655', 5, 'Active'),
    ('H009', N'3 B9 Đầm Trấu, Bạch Đằng, Quận Hai Bà Trưng', N'Hà Nội', N'Ha Noi Le Grand Hotel là đề xuất hàng đầu dành cho những tín đồ du lịch "bụi" mong muốn được nghỉ tại một khách sạn vừa thoải mái lại hợp túi tiền.', 'legrandhn@gmail.com', N'Ha Noi Le Grand Hotel', 'H009.jpg' , '0903441655', 3, 'Active'),
    ('H010', N'3 Lưu Tấn Tài, Phường 5', N'Cà Mau', N'Sao Kim Hotel Ca Mau là đề xuất hàng đầu dành cho những tín đồ du lịch "bụi" mong muốn được nghỉ tại một khách sạn vừa thoải mái lại hợp túi tiền.', 'saokim@gmail.com', N'Sao Kim Hotel Ca Mau', 'H010.jpg' , '0290650650', 2, 'Active'),
    ('H011', N'63 Nguyễn Vĩnh Nghiệp, Phường 2', N'Bạc Liêu', N'ROYAL HOTEL 2 là lựa chọn sáng giá dành cho những ai đang tìm kiếm một trải nghiệm xa hoa đầy thú vị trong kỳ nghỉ của mình. ', 'royalhotel2@gmail.com', N'ROYAL HOTEL 2 Bac Lieu', 'H011.jpg' , '0291676767', 1, 'Active'),
    ('H012', N'91 Võ Thị Sáu, Phú Hội', N'Huế', N'Hãy tận hưởng thời gian vui vẻ cùng cả gia đình với hàng loạt tiện nghi giải trí tại Gardenia Hue Hotel, một khách sạn tuyệt vời phù hợp cho mọi kỳ nghỉ bên người thân.', 'gardeniahue@gmail.com', N'Gardenia Hue Hotel', 'H012.jpg' , '0234368636', 3, 'Active'),
    ('H013', N'22 Ba Cu , Phường 1', N'Vũng Tàu', N'Lưu trú tại Kim Minh Boutique là một lựa chọn đúng đắn khi quý khách đến thăm Phường 1.', 'kimminhboutique@gmail.com', N'Kim Minh Boutique Vung Tau', 'H013.jpg' , '0254650888', 1, 'Active'),
    ('H014', N'26 Tô Hiến Thành, phường 3', N'Đà Lạt', N'Không chỉ sở hữu vị trí giúp quý khách dễ dàng ghé thăm những địa điểm lý thú trong chuyến hành trình, Goldient Boutique Hotel cũng sẽ mang đến cho quý khách trải nghiệm lưu trú mỹ mãn.', 'goldientboutique@gmail.com', N'Goldient Boutique Da Lat', 'H014.jpg' , '0263662222', 4, 'Active'),
    ('H015', N'57-59 Đỗ Bí, Mỹ An, Ngũ Hành Sơn', N'Đà Nẵng', N'Davue Hotel Da Nang là đề xuất hàng đầu dành cho những tín đồ du lịch "bụi" mong muốn được nghỉ tại một khách sạn vừa thoải mái lại hợp túi tiền.', 'davue@gmail.com', N'Davue Hotel Da Nang', 'H015.jpg' , '0236381888', 3, 'Active'),
    ('H016', N'Bãi Khem, Phường An Thới, An Thới', N'Phú Quốc', N'Quý khách hãy tận hưởng những đêm đáng nhớ nhất cùng người thương của mình tại JW Marriott Phu Quoc Emerald Bay Resort & Spa', 'jwpq@gmail.com', N'JW Marriott Phú Quốc Emerald Bay Resort & Spa', 'H016.jpg' , '0297377999', 5, 'Active'),
    ('H017', N'14-16 Phạm Ngọc Thạch, Cái Khế, Ninh Kiều', N'Cần Thơ', N'Khách sạn này là lựa chọn hoàn hảo cho các kỳ nghỉ mát lãng mạn hay tuần trăng mật của các cặp đôi.', 'lion3ct@gmail.com', N'Lion 3 Hotel Can Tho', 'H017.jpg' , '0292377766', 3, 'Active'),
    ('H018', N'10 Củ Chi, Vĩnh Hải', N'Nha Trang', N'Senia Hotel Nha Trang là lựa chọn tuyệt vời cho quý khách với phòng chức năng rộng lớn, được trang bị đầy đủ để sẵn sàng đáp ứng mọi yêu cầu.', 'seniahotelnt@gmail.com', N'Senia Hotel Nha Trang', 'H018.jpg' , '0258390588', 4, 'Active'),
    ('H019', N'ĐT716, Mũi Né', N'Phan Thiết', N'Từ sự kiện doanh nghiệp đến họp mặt công ty, Mandala Cham Bay Mui Ne cung cấp đầy đủ các dịch vụ và tiện nghi đáp ứng mọi nhu cầu của quý khách và đồng nghiệp.', 'mandala@gmail.com', N'Mandala Cham Bay Mui Ne', 'H019.jpg' , '1900088882', 4, 'Active'),
    ('H020', N'25 Tháng 4, Bạch Đằng, phường Bạch Đằng', N'Hạ Long', N'Khách sạn này là lựa chọn hoàn hảo cho các kỳ nghỉ mát lãng mạn hay tuần trăng mật của các cặp đôi. Quý khách hãy tận hưởng những đêm đáng nhớ nhất cùng người thương của mình tại Diamond Luxury Hotel Ha Long', 'diamond@gmail.com', N'Diamond Luxury Hotel Ha Long', 'H020.jpg' , '0203362888', 3, 'Active'),
    ('H021', N'54 Liễu Giai, Cống Vị, Quận Ba Đình', N'Hà Nội', N'Lotte Hotel Hanoi là lựa chọn tuyệt vời cho quý khách với phòng chức năng rộng lớn, được trang bị đầy đủ để sẵn sàng đáp ứng mọi yêu cầu.', 'lottehn@gmail.com', N'Lotte Hotel Hanoi', 'H021.jpg' , '0233331000', 5, 'Active'),
    ('H022', N'81 Phan Ngọc Hiển, phường 4', N'Cà Mau', N'Khách sạn này là lựa chọn hoàn hảo cho các kỳ nghỉ mát lãng mạn hay tuần trăng mật của các cặp đôi.', 'phucuongcamau@gmail.com', N'Phu Cuong Hotel Ca Mau', 'H022.jpg' , '0826889088', 4, 'Active'),
    ('H023', N'36 Trần Quang Diệu, Quận 1, Phường 1', N'Bạc Liêu', N'New Palace Bac Lieu Hotel là lựa chọn sáng giá dành cho những ai đang tìm kiếm một trải nghiệm xa hoa đầy thú vị trong kỳ nghỉ của mình.', 'newpalacebl@gmail.com', N'New Palace Bac Lieu Hotel', 'H023.jpg' , '0291394988', 4, 'Active'),
    ('H024', N'6/10 Võ Thị Sáu, Phú Hội', N'Huế', N'Không chỉ sở hữu vị trí giúp quý khách dễ dàng ghé thăm những địa điểm lý thú trong chuyến hành trình, Pao Hotel Hue cũng sẽ mang đến cho quý khách trải nghiệm lưu trú mỹ mãn.', 'paohotelhue@gmail.com', N'Pao Hotel Hue', 'H024.jpg' , '0905564275', 2, 'Active'),
    ('H025', N'720A đường Điện Biên Phủ, Phường 22, Quận Bình Thạnh', N'TP. Hồ Chí Minh', N'Vinpearl Landmark 81, Autograph Collection là lựa chọn sáng giá dành cho những ai đang tìm kiếm một trải nghiệm xa hoa đầy thú vị trong kỳ nghỉ của mình.', 'lm81@gmail.com', N'Vinpearl Landmark 81', 'H025.jpg' , '0283971888', 5, 'Active')
GO


insert into rooms values
('R0001','Available','H001','STANDARD'),
('R0002','Available','H001','STANDARD2'),
('R0003','Available','H001','DELUXE'),
('R0004','Available','H001','DELUXE2'),
('R0005','Available','H001','DELUXE4'),
('R0006','Available','H002','SINGLE'),
('R0007','Available','H002','DOUBLE'),
('R0008','Available','H002','TWIN'),
('R0009','Available','H002','TRIPLE'),
('R0010','Available','H002','FAMILY'),
('R0011','Available','H003','STANDARD'),
('R0012','Available','H003','STANDARD2'),
('R0013','Available','H003','DELUXE'),
('R0014','Available','H003','DELUXE2'),
('R0015','Available','H003','DELUXE4'),
('R0016','Available','H004','SINGLE'),
('R0017','Available','H004','DOUBLE'),
('R0018','Available','H004','TWIN'),
('R0019','Available','H004','TRIPLE'),
('R0020','Available','H005','FAMILY'),
('R0021','Available','H005','STANDARD'),
('R0022','Available','H005','STANDARD2'),
('R0023','Available','H005','DELUXE'),
('R0024','Available','H005','DELUXE2'),
('R0025','Available','H005','DELUXE4'),
('R0026','Available','H006','SINGLE'),
('R0027','Available','H006','DOUBLE'),
('R0028','Available','H006','TWIN'),
('R0029','Available','H006','TRIPLE'),
('R0030','Available','H006','FAMILY'),
('R0031','Available','H007','STANDARD'),
('R0032','Available','H007','STANDARD2'),
('R0033','Available','H007','DELUXE'),
('R0034','Available','H007','DELUXE2'),
('R0035','Available','H007','DELUXE4'),
('R0036','Available','H008','SINGLE'),
('R0037','Available','H008','DOUBLE'),
('R0038','Available','H008','TWIN'),
('R0039','Available','H008','TRIPLE'),
('R0040','Available','H008','FAMILY'),
('R0041','Available','H009','STANDARD'),
('R0042','Available','H009','STANDARD2'),
('R0043','Available','H009','DELUXE'),
('R0044','Available','H009','DELUXE2'),
('R0045','Available','H009','DELUXE4'),
('R0046','Available','H010','SINGLE'),
('R0047','Available','H010','DOUBLE'),
('R0048','Available','H010','TWIN'),
('R0049','Available','H010','TRIPLE'),
('R0050','Available','H010','FAMILY'),
('R0051','Available','H011','STANDARD'),
('R0052','Available','H012','STANDARD2'),
('R0053','Available','H013','DELUXE'),
('R0054','Available','H014','DELUXE2'),
('R0055','Available','H015','DELUXE4'),
('R0056','Available','H016','SINGLE'),
('R0057','Available','H017','DOUBLE'),
('R0058','Available','H018','TWIN'),
('R0059','Available','H019','TRIPLE'),
('R0060','Available','H020','FAMILY'),
('R0061','Available','H021','STANDARD'),
('R0062','Available','H022','STANDARD2'),
('R0063','Available','H023','DELUXE'),
('R0064','Available','H024','DELUXE2'),
('R0065','Available','H025','DELUXE4')



insert into booking values('BKN1','2024-01-20','2024-01-24','Done',300000,'R0001','dungnhps26555@fpt.edu.vn'),						
('BKN2','2024-01-01','2024-01-03','Done',400000,'R0002','dungnhps26555@fpt.edu.vn'),						
('BKN3','2024-01-07','2024-01-09','Done',500000,'R0003','thuannnmps26548@fpt.edu.vn'),						
('BKN4','2024-01-02','2024-01-04','Done',250000,'R0004','thuannnmps26548@fpt.edu.vn'),						
('BKN5','2024-01-08','2024-01-10','Done',324000,'R0005','giauntnps26628@fpt.edu.vn'),						
('BKN6','2024-01-03','2024-01-05','Done',660000,'R0006','giauntnps26628@fpt.edu.vn'),						
('BKN7','2024-01-08','2024-01-11','Done',724000,'R0007','huydgps26598@fpt.edu.vn'),						
('BKN8','2024-01-04','2024-01-07','Done',120000,'R0008','huydgps26598@fpt.edu.vn'),						
('BKN9','2024-01-09','2024-01-12','Done',460000,'R0009','dungnhps26555@fpt.edu.vn'),						
('BKN10','2024-01-10','2024-01-15','Done',130000,'R0010','thuannnmps26548@fpt.edu.vn')					


select * from hotels
select * from room_type
select * from rooms
select * from users
select * from wallet
select * from accounts
select * from booking



