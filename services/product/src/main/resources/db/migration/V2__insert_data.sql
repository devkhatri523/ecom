insert into category (id, description, name)
values
    (nextval('category_seq'), 'Electronic devices and gadgets', 'Electronics'),
    (nextval('category_seq'), 'Household furniture and fixtures', 'Furniture'),
    (nextval('category_seq'), 'Books and other reading materials', 'Books'),
    (nextval('category_seq'), 'Clothing and accessories', 'Clothing');

-- Insert sample data into product table
insert into product (id, description, name, available_quantity, price, category_id)
values
    (nextval('product_seq'), 'Smartphone with 6GB RAM and 128GB storage', 'Smartphone', 100, 299.99, (select id from category where name = 'Electronics')),
    (nextval('product_seq'), '4K Ultra HD Smart TV 55 inch', 'Smart TV', 50, 599.99, (select id from category where name = 'Electronics')),
    (nextval('product_seq'), 'Ergonomic office chair', 'Office Chair', 200, 149.99, (select id from category where name = 'Furniture')),
    (nextval('product_seq'), 'Modern wooden dining table', 'Dining Table', 20, 799.99, (select id from category where name = 'Furniture')),
    (nextval('product_seq'), 'Novel - "The Great Gatsby"', 'Novel', 300, 10.99, (select id from category where name = 'Books')),
    (nextval('product_seq'), 'Science Fiction - "Dune"', 'Science Fiction Book', 150, 15.99, (select id from category where name = 'Books')),
    (nextval('product_seq'), 'Mens Casual Shirt', 'Casual Shirt', 500, 25.99, (select id from category where name = 'Clothing')),
    (nextval('product_seq'), 'Womens Evening Gown', 'Evening Gown', 80, 99.99, (select id from category where name = 'Clothing'));