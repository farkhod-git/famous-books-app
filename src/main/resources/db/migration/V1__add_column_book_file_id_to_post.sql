alter table posts
    add column book_file_id uuid references attachments (id);