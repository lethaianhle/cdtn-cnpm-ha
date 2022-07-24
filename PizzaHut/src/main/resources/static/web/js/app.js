function chuyenDoi(type, element) {
    let tabs = document.getElementsByClassName('item');
    for (i = 0; i < tabs.length; i++) {
        tabs[i].style.color = '#333333';
    };

    element.style.color = '#ffffff';


    document.getElementById(type).style.display = 'block';
    switch (type) {
        case 'sanphamkhuyenmai':
            document.getElementById('sanphamnoibat').style.display = 'none';
            document.getElementById('tatcasanpham').style.display = 'none';
            break;
        case 'sanphamnoibat':
                document.getElementById('tatcasanpham').style.display = 'none';
                document.getElementById('sanphamkhuyenmai').style.display = 'none';
                break;
        case 'tatcasanpham':
                document.getElementById('sanphamnoibat').style.display = 'none';
                document.getElementById('sanphamkhuyenmai').style.display = 'none';
                break;
    };
};





