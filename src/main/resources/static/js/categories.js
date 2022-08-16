function getIndex(list, id) {
    for (var i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }
    return -1;
}

var categoryApi = Vue.resource('/categories{/id}');

Vue.component('category-form', {
    props: ['categories', 'categoryAttr'],
    data: function () {
        return {
            id: '',
            name: '',
            description: '',
            image: '',
            parentCategoryId: '',
            code: ''
        }
    },
    watch: {
        categoryAttr: function (newValue) {
            this.id = newValue.id;
            this.name = newValue.name;
            this.description = newValue.description;
            this.image = newValue.image;
            this.parentCategoryId = newValue.parentCategoryId;
            this.code = newValue.code;

        }
    },
    template: '<div>' +
        '<input type="text" placeholder="Write name" v-model="name"/>' +
        '<input type="text" placeholder="Write description" v-model="description"/>' +
        '<input type="text" placeholder="Write image" v-model="image"/>' +
        '<input type="text" placeholder="Write parent сategory id" v-model="parentCategoryId"/>' +
        '<input type="text" placeholder="Write code" v-model="code"/>' +
        '<input type="button" value="Save" @click="save"/>' +
        '</div>',
    methods: {
        save: function () {
            var category = {
                id: this.id,
                name: this.name,
                description: this.description,
                image: this.image,
                parentCategoryId: this.parentCategoryId,
                code: this.code
            };
            if (this.id) {
                categoryApi.update(category)
                    .then(result =>
                        result.json().then(data => {
                            var index = getIndex(this.categories, data.id);
                            this.id = '';
                            this.name = '';
                            this.description = '';
                            this.image = '';
                            this.parentCategoryId = '';
                            this.code = '';
                        }))
            } else {
                categoryApi.save({}, category).then(result =>
                    result.json().then(data => {
                        this.categories.push(data);
                        this.id = '';
                        this.name = '';
                        this.description = '';
                        this.image = '';
                        this.parentCategoryId = '';
                        this.code = '';
                    })
                )
            }
        },
    }
})

Vue.component('categories-row', {
    props: ['category', 'editCategory', 'categories'],
    template: '<tr>' +
        '<td>{{category.name}}</td>' +
        '<td>{{category.description}}</td>' +
        '<td><img :src=category.image width="150" height="auto" alt=""></td>' +
        '<td>{{category.code}}</td>' +
        '<td><input type="button" value="Edit" @click="edit" />' +
        '<input type="button" value="delete" @click="del" /></td>' +
        '</tr>',
    methods: {
        edit: function () {
            this.editCategory(this.category)
        },
        del: function() {
            categoryApi.remove({id: this.category.id}).then(result => {
                if (result.ok) {
                    this.categories.splice(this.categories.indexOf(this.category), 1)
                }
            })
        }
    }
})

Vue.component('categories-list', {
    props: ['categories'],
    data: function () {
        return {
            category: null
        }
    },
    template:
        '<div>' +
        '<category-form :categories="categories" :categoryAttr="category"/>' +
        '<table class="table">' +
        '<thead>' +
        '<tr>' +
        '<th scope="col">Name</th>' +
        '<th scope="col">Description</th>' +
        '<th scope="col">Image</th>' +
        '<th scope="col">Code</th>' +
        '<th scope="col"></th>' +
        '</tr>' +
        '</thead>' +
        '<tbody>' +
        '<categories-row v-for="category in categories" ' +
        ':category="category" ' +
        ':key="category.id" ' +
        ':editCategory="editCategory" ' +
        ':categories="categories"/>' +
        '</tbody>' +
        '</table>' +
        '</div>',
    created: function () {
        categoryApi.get().then(result =>
            result.json().then(data =>
                data.forEach(category =>
                    this.categories.push(category))
            )
        )
    },
    methods: {
        editCategory: function (category) {
            this.category = category;
        }
    }
});

var app = new Vue({
    el: '#category',
    template: '<categories-list :categories="categories"/>',
    data: {
        categories: []
    }
});