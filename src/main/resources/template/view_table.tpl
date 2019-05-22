<template>
    <div>
        <Table ref="dataTable" stripe :loading="table.loading" :columns="table.tableColumns" :data="table.tableRows"
               style="margin-top:20px;" @on-selection-change="changeSelection" @on-sort-change="changeSort"></Table>
        <div style="margin: 20px; overflow: hidden">
            <div style="float: right;">
                <Page :total="pager.total" :current="pager.pageNo" @on-change="changePageNo" @on-page-size-change="changePageSize"
                      showSizer showTotal></Page>
            </div>
        </div>
    </div>
</template>

<script>
    import * as utils from '@/api/utils-v2'

    export default {
        name: '{zywork.beanName}TableMain',
        data() {
            return {
                urls: {
                    searchUrl: '/{zywork.mappingUrl}/admin/pager-cond',
                    activeUrl: '/{zywork.mappingUrl}/admin/active',
                    removeUrl: '/{zywork.mappingUrl}/admin/remove/'
                },
                pager: {
                    pageNo: 1,
                    pageSize: 10,
                    total: 0
                },
                searchOpts: {
                    sortColumn: null,
                    sortOrder: null,
                },
                table: {
                    loading: false,
                    tableColumns: [{
                        type: 'selection',
                        width: 45,
                        key: 'id',
                        align: 'center',
                        fixed: 'left'
                    },
                        {
                            width: 60,
                            align: 'center',
                            fixed: 'left',
                            render: (h, params) => {
                                return h('span', params.index + (this.pager.pageNo - 1) * this.pager.pageSize + 1)
                            }
                        },
                        {zywork.tableColumns}
                        {
                            title: '激活状态',
                            key: 'isActive',
                            minWidth: 100,
                            align: 'center',
                            render: (h, params) => {
                                return h('i-switch', {
                                    props: {
                                        size: 'large',
                                        value: params.row.isActive === 0
                                    },
                                    style: {
                                        marginRight: '5px'
                                    },
                                    on: {
                                        'on-change': (status) => {
                                            this.active(params.row)
                                        }
                                    }
                                }, [
                                    h('span', {
                                        slot: 'open'
                                    }, '激活'),
                                    h('span', {
                                        slot: 'close'
                                    }, '冻结')
                                ])
                            }
                        },
                        {
                            title: "操作",
                            key: "action",
                            width: 120,
                            align: "center",
                            fixed: "right",
                            render: (h, params) => {
                                return h(
                                    "Dropdown",
                                    {
                                        on: {
                                            "on-click": itemName => {
                                                this.userOpt(itemName, params.row);
                                            }
                                        },
                                        props: {
                                            transfer: true
                                        }
                                    },
                                    [
                                        h(
                                            "Button",
                                            {
                                                props: {
                                                    type: "primary",
                                                    size: "small"
                                                }
                                            },
                                            [
                                                "选择操作 ",
                                                h("Icon", {
                                                    props: {
                                                        type: "ios-arrow-down"
                                                    }
                                                })
                                            ]
                                        ),
                                        h(
                                            "DropdownMenu",
                                            {
                                                slot: "list"
                                            },
                                            [
                                                h(
                                                    "DropdownItem",
                                                    {
                                                        props: {
                                                            name: "showEdit"
                                                        }
                                                    },
                                                    "编辑"
                                                ),
                                                h(
                                                    "DropdownItem",
                                                    {
                                                        props: {
                                                            name: "showDetail"
                                                        }
                                                    },
                                                    "详情"
                                                ),
                                                h(
                                                    "DropdownItem",
                                                    {
                                                        props: {
                                                            name: "remove"
                                                        }
                                                    },
                                                    [
                                                        h(
                                                            "span",
                                                            {
                                                                style: {
                                                                    color: "red"
                                                                }
                                                            },
                                                            "删除"
                                                        )
                                                    ]
                                                ),
                                                h(
                                                    'DropdownItem',
                                                    {
                                                        props: {
                                                            name: 'role'
                                                        }
                                                    },
                                                    '角色'
                                                ),
                                            ]
                                        )
                                    ]
                                );
                            }
                        }
                    ],
                    tableRows: [],
                    selections: []
                }
            }
        },
        computed: {},
        mounted() {
            this.search()
        },
        methods: {
            search() {
                this.$emit('searchTable')
            },
            userOpt(itemName, row) {
                if (itemName === "showEdit") {
                    this.$emit('showEditModal', JSON.parse(JSON.stringify(row)))
                } else if (itemName === "showDetail") {
                    this.$emit('showDetailModal', row)
                } else if (itemName === "remove") {
                    utils.remove(this, row);
                }
            },
            active(row) {
                utils.active(this, row)
            },
            changeSelection(selections) {
                utils.changeSelections(this, selections)
            },
            changeSort(sortColumn) {
                utils.changeSort(this, sortColumn)
            },
            changePageNo(pageNo) {
                utils.changePageNo(this, pageNo)
            },
            changePageSize(pageSize) {
                utils.changePageSize(this, pageSize)
            }
        }
    }
</script>

<style>
</style>
