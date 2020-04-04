--Country
insert into country (code, name) values ('AR', 'Argentina');
insert into country (code, name) values ('BR', 'Brazil');
insert into country (code, name) values ('CH', 'China');
insert into country (code, name) values ('GE', 'Germany');
insert into country (code, name) values ('JP', 'Japan');
insert into country (code, name) values ('PL', 'Poland');
insert into country (code, name) values ('US', 'United States');

--Manufacturer
insert into manufacturer (name, origin_country_code) values ('Marcopolo', 'BR');
insert into manufacturer (name, origin_country_code) values ('Chery', 'CH');
insert into manufacturer (name, origin_country_code) values ('Lifan', 'CH');
insert into manufacturer (name, origin_country_code) values ('BMW', 'GE');
insert into manufacturer (name, origin_country_code) values ('Mercedes-Benz', 'GE');
insert into manufacturer (name, origin_country_code) values ('Opel', 'GE');
insert into manufacturer (name, origin_country_code) values ('Porsche', 'GE');
insert into manufacturer (name, origin_country_code) values ('Volkswagen', 'GE');
insert into manufacturer (name, origin_country_code) values ('Mazda', 'JP');
insert into manufacturer (name, origin_country_code) values ('Nissan', 'JP');
insert into manufacturer (name, origin_country_code) values ('Ford', 'US');
insert into manufacturer (name, origin_country_code) values ('Tesla', 'US');

--Car
insert into car (manufacturer_id, name) values (1, 'Viaggio');
insert into car (manufacturer_id, name) values (2, 'Tiggo 8');
insert into car (manufacturer_id, name) values (2, 'Arrizo 7');
insert into car (manufacturer_id, name) values (3, 'Foison');
insert into car (manufacturer_id, name) values (3, 'X80');
insert into car (manufacturer_id, name) values (3, 'Maiwei');
insert into car (manufacturer_id, name) values (4, 'Z4 Roadster');
insert into car (manufacturer_id, name) values (4, 'X7 M');
insert into car (manufacturer_id, name) values (4, 'Series 8 Coupe');
insert into car (manufacturer_id, name) values (5, 'C117 CLA');
insert into car (manufacturer_id, name) values (5, 'W205 C');
insert into car (manufacturer_id, name) values (5, 'X247 GLB');
insert into car (manufacturer_id, name) values (5, 'EQC');
insert into car (manufacturer_id, name) values (6, 'Insignia A');
insert into car (manufacturer_id, name) values (6, 'Grandland X');
insert into car (manufacturer_id, name) values (7, 'Carrera GT');
insert into car (manufacturer_id, name) values (7, '918 Spyder');
insert into car (manufacturer_id, name) values (8, 'Amarok');
insert into car (manufacturer_id, name) values (8, 'Santana');
insert into car (manufacturer_id, name) values (8, 'XL 1');
insert into car (manufacturer_id, name) values (9, 'Mazda 6');
insert into car (manufacturer_id, name) values (9, 'MX-5');
insert into car (manufacturer_id, name) values (10, 'Pathfinder');
insert into car (manufacturer_id, name) values (10, 'X-Trail');
insert into car (manufacturer_id, name) values (10, 'Rogue');
insert into car (manufacturer_id, name) values (11, 'Ka');
insert into car (manufacturer_id, name) values (11, 'Fiesta');
insert into car (manufacturer_id, name) values (11, 'Focus');
insert into car (manufacturer_id, name) values (11, 'Mustang');
insert into car (manufacturer_id, name) values (11, 'Ecosport');
insert into car (manufacturer_id, name) values (11, 'Explorer');
insert into car (manufacturer_id, name) values (11, 'F-150');
insert into car (manufacturer_id, name) values (12, 'Model S');
insert into car (manufacturer_id, name) values (12, 'Model 3');

--Shop
insert into shop (name) values ('1620 Trawick Rd, Raleigh, NC');
insert into shop (name) values ('1292 Forgewood Ave, Sunnyvale, CA');
insert into shop (name) values ('302 US-60, Billings, MO');
insert into shop (name) values ('1910 US-50, Pueblo, CO');
insert into shop (name) values ('168 Centre St, Brooklyn, NY');
insert into shop (name) values ('1223 Sagamore Pkwy W, West Lafayette, IN');
insert into shop (name) values ('11555 Sorrento Valley Rd #201, San Diego, CA');
insert into shop (name) values ('330 E Kearney St, Springfield, MO');
insert into shop (name) values ('2216 S Neil St, Champaign, IL');
insert into shop (name) values ('1331 Stewart St, Seattle, WA');
insert into shop (name) values ('12010 S U.S. 71 Hwy, Grandview, MO');
insert into shop (name) values ('1100 Bradshaw Garden Dr Rd, Knoxville, TN');
insert into shop (name) values ('1812 Sycamore Rd, DeKalb, IL');
insert into shop (name) values ('2119 SE 192nd Ave, Camas, WA');
insert into shop (name) values ('603 Meacham Rd, Elk Grove Village, IL');

--Stock
insert into stock (car_id, shop_id, stock) values (1,1,   6);
insert into stock (car_id, shop_id, stock) values (1,2,  15);
insert into stock (car_id, shop_id, stock) values (1,3,   7);
insert into stock (car_id, shop_id, stock) values (1,4,   1);
insert into stock (car_id, shop_id, stock) values (1,5,  14);
insert into stock (car_id, shop_id, stock) values (1,6,  19);
insert into stock (car_id, shop_id, stock) values (1,7,   7);
insert into stock (car_id, shop_id, stock) values (1,8,   8);
insert into stock (car_id, shop_id, stock) values (1,9,  11);
insert into stock (car_id, shop_id, stock) values (1,10,  9);
insert into stock (car_id, shop_id, stock) values (1,11,  1);
insert into stock (car_id, shop_id, stock) values (1,12, 17);
insert into stock (car_id, shop_id, stock) values (1,13,  0);
insert into stock (car_id, shop_id, stock) values (1,14, 13);
insert into stock (car_id, shop_id, stock) values (1,15,  3);
insert into stock (car_id, shop_id, stock) values (2,1,  19);
insert into stock (car_id, shop_id, stock) values (2,2,  11);
insert into stock (car_id, shop_id, stock) values (2,3,   0);
insert into stock (car_id, shop_id, stock) values (2,4,   9);
insert into stock (car_id, shop_id, stock) values (2,5,   5);
insert into stock (car_id, shop_id, stock) values (2,6,  15);
insert into stock (car_id, shop_id, stock) values (2,7,  16);
insert into stock (car_id, shop_id, stock) values (2,8,   2);
insert into stock (car_id, shop_id, stock) values (2,9,  12);
insert into stock (car_id, shop_id, stock) values (2,10,  9);
insert into stock (car_id, shop_id, stock) values (2,11,  3);
insert into stock (car_id, shop_id, stock) values (2,12,  7);
insert into stock (car_id, shop_id, stock) values (2,13, 16);
insert into stock (car_id, shop_id, stock) values (2,14, 13);
insert into stock (car_id, shop_id, stock) values (2,15,  1);
insert into stock (car_id, shop_id, stock) values (3,1,  18);
insert into stock (car_id, shop_id, stock) values (3,2,  18);
insert into stock (car_id, shop_id, stock) values (3,3,   9);
insert into stock (car_id, shop_id, stock) values (3,4,   2);
insert into stock (car_id, shop_id, stock) values (3,5,  17);
insert into stock (car_id, shop_id, stock) values (3,6,  13);
insert into stock (car_id, shop_id, stock) values (3,7,  18);
insert into stock (car_id, shop_id, stock) values (3,8,  17);
insert into stock (car_id, shop_id, stock) values (3,9,  13);
insert into stock (car_id, shop_id, stock) values (3,10,  6);
insert into stock (car_id, shop_id, stock) values (3,11,  6);
insert into stock (car_id, shop_id, stock) values (3,12,  5);
insert into stock (car_id, shop_id, stock) values (3,13, 12);
insert into stock (car_id, shop_id, stock) values (3,14, 15);
insert into stock (car_id, shop_id, stock) values (3,15,  5);
insert into stock (car_id, shop_id, stock) values (4,1,   2);
insert into stock (car_id, shop_id, stock) values (4,2,   7);
insert into stock (car_id, shop_id, stock) values (4,3,  18);
insert into stock (car_id, shop_id, stock) values (4,4,   2);
insert into stock (car_id, shop_id, stock) values (4,5,   8);
insert into stock (car_id, shop_id, stock) values (4,6,  19);
insert into stock (car_id, shop_id, stock) values (4,7,   6);
insert into stock (car_id, shop_id, stock) values (4,8,  19);
insert into stock (car_id, shop_id, stock) values (4,9,  13);
insert into stock (car_id, shop_id, stock) values (4,10, 18);
insert into stock (car_id, shop_id, stock) values (4,11,  3);
insert into stock (car_id, shop_id, stock) values (4,12, 13);
insert into stock (car_id, shop_id, stock) values (4,13, 11);
insert into stock (car_id, shop_id, stock) values (4,14,  5);
insert into stock (car_id, shop_id, stock) values (4,15, 14);
insert into stock (car_id, shop_id, stock) values (5,1,   8);
insert into stock (car_id, shop_id, stock) values (5,2,   2);
insert into stock (car_id, shop_id, stock) values (5,3,  11);
insert into stock (car_id, shop_id, stock) values (5,4,  17);
insert into stock (car_id, shop_id, stock) values (5,5,   5);
insert into stock (car_id, shop_id, stock) values (5,6,  16);
insert into stock (car_id, shop_id, stock) values (5,7,   5);
insert into stock (car_id, shop_id, stock) values (5,8,  12);
insert into stock (car_id, shop_id, stock) values (5,9,   2);
insert into stock (car_id, shop_id, stock) values (5,10,  5);
insert into stock (car_id, shop_id, stock) values (5,11, 17);
insert into stock (car_id, shop_id, stock) values (5,12,  6);
insert into stock (car_id, shop_id, stock) values (5,13,  2);
insert into stock (car_id, shop_id, stock) values (5,14,  8);
insert into stock (car_id, shop_id, stock) values (5,15,  4);
insert into stock (car_id, shop_id, stock) values (6,1,   6);
insert into stock (car_id, shop_id, stock) values (6,2,  18);
insert into stock (car_id, shop_id, stock) values (6,3,   1);
insert into stock (car_id, shop_id, stock) values (6,4,   6);
insert into stock (car_id, shop_id, stock) values (6,5,  18);
insert into stock (car_id, shop_id, stock) values (6,6,  14);
insert into stock (car_id, shop_id, stock) values (6,7,   7);
insert into stock (car_id, shop_id, stock) values (6,8,  10);
insert into stock (car_id, shop_id, stock) values (6,9,   8);
insert into stock (car_id, shop_id, stock) values (6,10,  5);
insert into stock (car_id, shop_id, stock) values (6,11, 12);
insert into stock (car_id, shop_id, stock) values (6,12,  8);
insert into stock (car_id, shop_id, stock) values (6,13, 15);
insert into stock (car_id, shop_id, stock) values (6,14, 16);
insert into stock (car_id, shop_id, stock) values (6,15, 16);
insert into stock (car_id, shop_id, stock) values (7,1,   5);
insert into stock (car_id, shop_id, stock) values (7,2,   9);
insert into stock (car_id, shop_id, stock) values (7,3,   6);
insert into stock (car_id, shop_id, stock) values (7,4,   2);
insert into stock (car_id, shop_id, stock) values (7,5,  12);
insert into stock (car_id, shop_id, stock) values (7,6,   5);
insert into stock (car_id, shop_id, stock) values (7,7,  11);
insert into stock (car_id, shop_id, stock) values (7,8,   1);
insert into stock (car_id, shop_id, stock) values (7,9,   6);
insert into stock (car_id, shop_id, stock) values (7,10, 16);
insert into stock (car_id, shop_id, stock) values (7,11,  9);
insert into stock (car_id, shop_id, stock) values (7,12, 12);
insert into stock (car_id, shop_id, stock) values (7,13, 10);
insert into stock (car_id, shop_id, stock) values (7,14,  7);
insert into stock (car_id, shop_id, stock) values (7,15, 17);
insert into stock (car_id, shop_id, stock) values (8,1,   8);
insert into stock (car_id, shop_id, stock) values (8,2,   9);
insert into stock (car_id, shop_id, stock) values (8,3,  10);
insert into stock (car_id, shop_id, stock) values (8,4,   0);
insert into stock (car_id, shop_id, stock) values (8,5,  17);
insert into stock (car_id, shop_id, stock) values (8,6,  18);
insert into stock (car_id, shop_id, stock) values (8,7,  17);
insert into stock (car_id, shop_id, stock) values (8,8,   8);
insert into stock (car_id, shop_id, stock) values (8,9,   0);
insert into stock (car_id, shop_id, stock) values (8,10,  0);
insert into stock (car_id, shop_id, stock) values (8,11, 17);
insert into stock (car_id, shop_id, stock) values (8,12,  1);
insert into stock (car_id, shop_id, stock) values (8,13, 14);
insert into stock (car_id, shop_id, stock) values (8,14, 19);
insert into stock (car_id, shop_id, stock) values (8,15, 10);
insert into stock (car_id, shop_id, stock) values (9,1,   5);
insert into stock (car_id, shop_id, stock) values (9,2,  12);
insert into stock (car_id, shop_id, stock) values (9,3,  12);
insert into stock (car_id, shop_id, stock) values (9,4,   7);
insert into stock (car_id, shop_id, stock) values (9,5,   4);
insert into stock (car_id, shop_id, stock) values (9,6,  16);
insert into stock (car_id, shop_id, stock) values (9,7,   8);
insert into stock (car_id, shop_id, stock) values (9,8,   3);
insert into stock (car_id, shop_id, stock) values (9,9,   0);
insert into stock (car_id, shop_id, stock) values (9,10, 10);
insert into stock (car_id, shop_id, stock) values (9,11, 19);
insert into stock (car_id, shop_id, stock) values (9,12, 18);
insert into stock (car_id, shop_id, stock) values (9,13, 15);
insert into stock (car_id, shop_id, stock) values (9,14,  4);
insert into stock (car_id, shop_id, stock) values (9,15,  1);
insert into stock (car_id, shop_id, stock) values (10,1,  5);
insert into stock (car_id, shop_id, stock) values (10,2, 10);
insert into stock (car_id, shop_id, stock) values (10,3, 12);
insert into stock (car_id, shop_id, stock) values (10,4, 15);
insert into stock (car_id, shop_id, stock) values (10,5,  7);
insert into stock (car_id, shop_id, stock) values (10,6, 14);
insert into stock (car_id, shop_id, stock) values (10,7,  7);
insert into stock (car_id, shop_id, stock) values (10,8,  1);
insert into stock (car_id, shop_id, stock) values (10,9,  9);
insert into stock (car_id, shop_id, stock) values (10,10, 0);
insert into stock (car_id, shop_id, stock) values (10,11, 3);
insert into stock (car_id, shop_id, stock) values (10,12, 5);
insert into stock (car_id, shop_id, stock) values (10,13, 6);
insert into stock (car_id, shop_id, stock) values (10,14, 2);
insert into stock (car_id, shop_id, stock) values (10,15, 2);
insert into stock (car_id, shop_id, stock) values (11,1,  4);
insert into stock (car_id, shop_id, stock) values (11,2, 10);
insert into stock (car_id, shop_id, stock) values (11,3,  3);
insert into stock (car_id, shop_id, stock) values (11,4,  0);
insert into stock (car_id, shop_id, stock) values (11,5, 17);
insert into stock (car_id, shop_id, stock) values (11,6, 13);
insert into stock (car_id, shop_id, stock) values (11,7,  9);
insert into stock (car_id, shop_id, stock) values (11,8, 12);
insert into stock (car_id, shop_id, stock) values (11,9,  0);
insert into stock (car_id, shop_id, stock) values (11,10, 0);
insert into stock (car_id, shop_id, stock) values (11,11,10);
insert into stock (car_id, shop_id, stock) values (11,12,15);
insert into stock (car_id, shop_id, stock) values (11,13,17);
insert into stock (car_id, shop_id, stock) values (11,14, 4);
insert into stock (car_id, shop_id, stock) values (11,15, 1);
insert into stock (car_id, shop_id, stock) values (12,1, 10);
insert into stock (car_id, shop_id, stock) values (12,2,  2);
insert into stock (car_id, shop_id, stock) values (12,3,  9);
insert into stock (car_id, shop_id, stock) values (12,4,  4);
insert into stock (car_id, shop_id, stock) values (12,5, 11);
insert into stock (car_id, shop_id, stock) values (12,6,  9);
insert into stock (car_id, shop_id, stock) values (12,7, 10);
insert into stock (car_id, shop_id, stock) values (12,8,  4);
insert into stock (car_id, shop_id, stock) values (12,9,  3);
insert into stock (car_id, shop_id, stock) values (12,10, 8);
insert into stock (car_id, shop_id, stock) values (12,11, 0);
insert into stock (car_id, shop_id, stock) values (12,12,13);
insert into stock (car_id, shop_id, stock) values (12,13, 3);
insert into stock (car_id, shop_id, stock) values (12,14, 7);
insert into stock (car_id, shop_id, stock) values (12,15,17);

commit;