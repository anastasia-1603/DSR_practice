function getIndex(list, id) {
    for (var i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }
    return -1;
}

var itemApi = Vue.resource('/items{/id}');
var categoryApi = Vue.resource('/categories{/id}')

Vue.component('item-form', {
    props: ['items', 'itemAttr', 'categories'],
    data: function () {
        return {
            id: '',
            name: '',
            description: '',
            image: '',
            category: {
                name: ''
            }
        }
    },
    watch: {
        itemAttr: function (newValue) {
            this.id = newValue.id;
            this.name = newValue.name;
            this.description = newValue.description;
            this.image = newValue.image;
            this.category = newValue.category;
        }
    },
    template: '<div>' +
        '<input type="text" placeholder="Write name" v-model="name"/>' +
        '<input type="text" placeholder="Write description" v-model="description"/>' +
        '<input type="text" placeholder="Insert link to image" v-model="image"/>' +
        '<categories :categories="categories" v-model="categories"/>' +
        '<input type="button" value="Save" @click="save"/>' +
        '</div>',
    methods: {

        save: function () {
            var item = {
                id: this.id,
                name: this.name,
                description: this.description,
                image: this.image,

            };
            if (this.id) {
                itemApi.update(item)
                    .then(result =>
                        result.json().then(data => {
                            var index = getIndex(this.items, data.id);
                            this.items.splice(index, 5, data);
                            this.name = '';
                            this.description = '';
                            this.image = '';
                            this.category = '';
                        }))
            } else {
                itemApi.save({}, item).then(result =>
                    result.json().then(data => {
                        this.items.push(data);
                        this.name = '';
                        this.description = '';
                        this.image = '';
                        this.category = '';
                    })
                )
            }
        }
    }
})

Vue.component('categories', {
    props: ['categories'],

    template: '<input list="categories" v-model="category"/>' +
        '<datalist id="categories"' +
        '<option v-for="c in categories" :value=c.name ' +
        '</datalist>'
});

Vue.component('items-row', {
    props: ['item', 'editItem', 'items'],
    template:
        '<tr>' +
        '<td>{{item.name}}</td>' +
        '<td>{{item.description}}</td>' +
        '<td><img :src=item.image width="150" height="auto" alt=""></td>' +
        '<td>{{item.category.name}}</td>' +
        '<td><input type="button" value="Edit" @click="edit" />' +
        '<input type="button" value="delete" @click="del" /></td>' +
        '</tr>',
    methods: {
        edit: function () {
            this.editItem(this.item)
        },
        del: function() {
            itemApi.remove({id: this.item.id}).then(result => {
                if (result.ok) {
                    this.items.splice(this.items.indexOf(this.item), 1)
                }
            })
        }
    }
})

Vue.component('items-list', {
    props: ['items'],
    data: function () {
        return {
            item: null,
            categories: categoryApi.get()
        }
    },
    template:
        '<div>' +
        '<item-form :items="items" :itemAttr="item" :categories="categories"/>' +
        '<table class="table">' +
        '<thead>' +
        '<tr>' +
        '<th scope="col">Name</th>' +
        '<th scope="col">Description</th>' +
        '<th scope="col">Image</th>' +
        '<th scope="col">Category</th>' +
        '<th scope="col"></th>' +
        '</tr>' +
        '</thead>' +
        '<tbody>' +
        '<items-row v-for="item in items" ' +
        ':item="item" ' +
        ':key="item.id" ' +
        ':editItem="editItem" ' +
        ':items="items"/>' +
        '</tbody>' +
        '</table>' +
        '</div>',
    created: function () {
        itemApi.get().then(result =>
            result.json().then(data =>
                data.forEach(item =>
                    this.items.push(item))
            )
        )
    },
    methods: {
        editItem: function (item) {
            this.item = item;
        }
    }
});

var app = new Vue({
    el: '#item',
    template: '<items-list :items="items"/>',
    data: {
        items: []
    }
});