$.fn.dataTable.ext.errMode = 'none';

$.extend( true, $.fn.dataTable.defaults, {
    autoWidth: true,
    bPaginate: false,
    bFilter: false,
    bInfo: false,
    responsive: true,
    processing: true,
    stateSave: false,
    select: true,
    searching: true,
    bLengthChange: false,
    dom: "<'row mb-1'<'col-sm-12 col-md-6 d-flex align-items-center justify-content-start'f><'col-sm-12 col-md-6 d-flex align-items-center justify-content-end'l>>" +
        "<'row'<'col-sm-12'tr>>" +
        "<'row'<'col-sm-12 col-md-5'i><'col-sm-12 col-md-7'p>>",
    oLanguage: {
        sEmptyTable: "데이타가 없습니다.",
        sZeroRecords: "검색 결과가 없습니다.",
        sLoadingRecords: "Loading..",
    }
} );
