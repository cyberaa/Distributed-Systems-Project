CREATE or replace FUNCTION check_nShares_update() RETURNS trigger AS $$
DECLARE
     c1  Cursor for
    select num_shares
    from accao;
    
BEGIN
     open c1;
     for r in c1
     loop
     update accao set estado = 'VENDIDO';
    end loop;
    commit;
END;
$$ LANGUAGE plpgsql;







