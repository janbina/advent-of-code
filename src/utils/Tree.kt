package utils

class TreeNode<T: Any>(
    val value: T,
) {

    private val children = mutableSetOf<TreeNode<T>>()

    var parent: TreeNode<T>? = null

    fun addChildren(children: TreeNode<T>) {
        this.children.add(children)
    }

    fun getChildren(): Set<TreeNode<T>> = children.toSet()
}
